import hudson.tasks.junit.TestResultAction

pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression', 'Custom'],
            description: 'Choose test suite: Smoke, Regression or Custom test by name'
        )

        string(
            name: 'TEST_NAME',
            defaultValue: '',
            description: 'Enter full test class name or method (e.g. com.example.LoginTest or com.example.LoginTest.myMethod)'
        )

        booleanParam(
            name: 'CLEAN',
            defaultValue: true,
            description: 'Run gradle clean before tests'
        )
    }

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-21-openjdk-amd64"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    def taskName

                    if (params.TEST_SUITE == "Custom" && params.TEST_NAME?.trim()) {
                        def tests = params.TEST_NAME.replaceAll('\\s', '')
                        taskName = "test --tests \"${tests}\""
                        echo "Running specific test(s): ${tests}"
                    } else if (params.TEST_SUITE == "Smoke") {
                        taskName = "test -Djunit.jupiter.tags=Smoke"
                        echo "Running all Smoke tests"
                    } else if (params.TEST_SUITE == "Regression") {
                        taskName = "test -Djunit.jupiter.tags=Regression"
                        echo "Running all Regression tests"
                    } else {
                        error "No test selected or TEST_NAME is empty for Custom"
                    }

                    def status = sh(
                        script: "./gradlew ${params.CLEAN ? 'clean ' : ''}${taskName} --no-daemon --info --continue",
                        returnStatus: true
                    )

                    currentBuild.result = (status == 0) ? 'SUCCESS' : 'UNSTABLE'
                }
            }
        }

        stage('Debug Allure Results') {
            steps {
                sh '''
                    if [ -d build/allure-results ]; then
                        echo "Allure results found:"
                        ls -la build/allure-results
                    else
                        echo "No Allure results found!"
                    fi
                '''
            }
        }
    }

    post {
        always {
            script {
                def testResult = junit(allowEmptyResults: true, testResults: "build/test-results/test/*.xml")

                if (fileExists('build/allure-results')) {
                    if (testResult != null) {
                        def totalTests = testResult.getTotalCount()
                        def passedTests = testResult.getPassCount()
                        def failedTests = testResult.getFailCount()

def passedPercentage = (totalTests > 0) ? (passedTests * 100 / (double)totalTests) : 0
def formattedPassedPercentage = String.format("%.2f", passedPercentage) + "%"

def failedPercentage = (totalTests > 0) ? (failedTests * 100 / (double)totalTests) : 0
def formattedFailedPercentage = String.format("%.2f", failedPercentage) + "%"

                        allure([
                            includeProperties: true,
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'build/allure-results']]
                        ])

                        def botToken = "8133371990:AAHoB2B54YGTPYMyv6khj4OYSc2MGs1mMi8"
                        def chatId = "8484572689"

                        // Убрал лишние пробелы и добавил явные \n
                        def messageText = "Results:\nEnvironment: env\nComment: some comment\nDuration: ${currentBuild.durationString}\nTotal scenarios: ${totalTests}\nTotal passed: ${passedTests} (${passedPercentage}%)\nTotal failed: ${failedTests} (${failedPercentage}%)\nReport available at the link: ${env.BUILD_URL}allure"

                        if (fileExists('PIPELINE.png')) {
                            sh """
                                curl -s -X POST \\
                                     -F "chat_id=${chatId}" \\
                                     -F "photo=@PIPELINE.png" \\
                                     -F "caption=${messageText}" \\
                                     "https://api.telegram.org/bot${botToken}/sendPhoto"
                            """
                        } else {
                            echo "File PIPELINE.png not found, skipping photo upload to Telegram."
                            sh """
                                curl -s -X POST \\
                                     --data-urlencode "chat_id=${chatId}" \\
                                     --data-urlencode "text=${messageText}" \\
                                     "https://api.telegram.org/bot${botToken}/sendMessage"
                            """
                        }
                    } else {
                        echo "JUnit test results not found, skipping Telegram notification."
                    }
                } else {
                    echo "Allure results folder not found, skipping Allure report and Telegram notification."
                }

                def reportDir = 'build/reports/tests/test'
                archiveArtifacts artifacts: "${reportDir}/**", allowEmptyArchive: true
            }
        }
    }
}