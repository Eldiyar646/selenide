pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression'],
            description: 'Choose test suite: Smoke or Regression'
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
                    def taskName = "${params.TEST_SUITE}Test"
                    echo "Running Gradle task: ${taskName}"

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

            // Allure report + отправка в Telegram
            script {
                if (fileExists('build/allure-results')) {
                    // Генерация Allure отчёта
                    allure([
                        includeProperties: true,
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'build/allure-results']]
                    ])

                    // Telegram
                    def botToken = "8133371990:AAHoB2B54YGTPYMyv6khj4OYSc2MGs1mMi8"
                    def chatId = "8484572689"

                    // Архивируем Allure отчёт
                    //sh "zip -r allure-report.zip build/allure-report"
                    //заменил на этот более стабильный
                    sh "zip -r allure-report.zip allure-report || true"

                    // Отправляем архив в Telegram
                    sh """
                        curl -s -X POST "https://api.telegram.org/bot${botToken}/sendDocument" \
                             -F chat_id=${chatId} \
                             -F document=@allure-report.zip \
                             -F caption="Allure Report for Jenkins build #${env.BUILD_NUMBER}"
                    """
                } else {
                    echo "Allure results folder not found, skipping Allure report."
                }
            }

            // HTML Gradle reports
            archiveArtifacts artifacts: "build/reports/tests/${params.TEST_SUITE}Test/**", allowEmptyArchive: true
        }
    }
}