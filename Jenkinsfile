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

        stage('Allure Report') {
            steps {
                sh './gradlew allureReport'
            }
        }
    }

    post {
        always {
            script {
                if (fileExists('build/reports/allure-report/allureReport/widgets/summary.json')) {
                    def summary = readJSON file: 'build/reports/allure-report/allureReport/widgets/summary.json'

                    def total = summary.statistic.total
                    def passed = summary.statistic.passed
                    def failed = summary.statistic.failed
                    def broken = summary.statistic.broken
                    def skipped = summary.statistic.skipped

                    allure([
                        includeProperties: true,
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'build/allure-results']]
                    ])

                    def botToken = "8133371990:AAHoB2B54YGTPYMyv6khj4OYSc2MGs1mMi8"
                    def chatId = "8484572689"

                    // формируем текст
                    def messageText = """📊 *Результаты тестов*
🕒 Duration: ${currentBuild.durationString.replace('and counting', '')}
📌 Total: ${total}
✅ Passed: ${passed}
❌ Failed: ${failed}
💥 Broken: ${broken}
⚠️ Skipped: ${skipped}
🔗 [Allure Report](${env.BUILD_URL}allure)
"""

                    // билдим ChartGenerator
                    sh "./gradlew classes"

                    // генерируем chart.png
                    sh """
                        java -cp build/classes/java/test:~/.gradle/caches/modules-2/files-2.1/* utils.ChartGenerator \
                        ${total} ${passed} ${failed} ${broken} ${skipped} chart.png
                    """

                    // отправляем текст + картинку в Telegram
                    sh """
                        curl -s -X POST \
                             -F "chat_id=${chatId}" \
                             -F "photo=@chart.png" \
                             -F "caption=${messageText}" \
                             -F "parse_mode=Markdown" \
                             "https://api.telegram.org/bot${botToken}/sendPhoto"
                    """
                } else {
                    echo "summary.json not found, skipping Telegram notification."
                }

                def reportDir = 'build/reports/tests/test'
                archiveArtifacts artifacts: "${reportDir}/**", allowEmptyArchive: true
            }
        }
    }
}