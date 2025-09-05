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
                        // Поддержка нескольких тестов через запятую
                        def tests = params.TEST_NAME.replaceAll('\\s','')
                        taskName = "test --tests \"${tests}\""
                        echo "Running specific test(s): ${tests}"
                    } else if (params.TEST_SUITE == "Smoke") {
                        taskName = "SmokeTest"
                        echo "Running all Smoke tests"
                    } else if (params.TEST_SUITE == "Regression") {
                        taskName = "RegressionTest"
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
            // JUnit XML reports
            junit allowEmptyResults: true, testResults: "build/test-results/test/*.xml"

            script {
                if (fileExists('build/allure-results')) {
                    // Генерация Allure отчёта
                    allure([
                        includeProperties: true,
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'build/allure-results']]
                    ])

                    // Отправка в Telegram
                    def botToken = "8133371990:AAHoB2B54YGTPYMyv6khj4OYSc2MGs1mMi8"
                    def chatId = "8484572689"
                    sh "zip -r allure-report.zip allure-report || true"
                    sh """
                        curl -s -X POST "https://api.telegram.org/bot${botToken}/sendDocument" \
                             -F chat_id=${chatId} \
                             -F document=@allure-report.zip \
                             -F caption="Allure Report for Jenkins build #${env.BUILD_NUMBER}"
                    """
                } else {
                    echo "Allure results folder not found, skipping Allure report."
                }

                // Архивируем отчеты Gradle
                def reportDir = (params.TEST_SUITE == 'Custom') ? 'build/reports/tests/test' : "build/reports/tests/${params.TEST_SUITE}Test"
                archiveArtifacts artifacts: "${reportDir}/**", allowEmptyArchive: true
            }
        }
    }
}