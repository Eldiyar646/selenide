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

        stage('Build Chart Generator') {
            steps {
                sh "./gradlew shadowJar"
            }
        }

    }

    post {
        always {
            script {
                // Публикуем Allure-отчет. Это создаст директорию 'build/reports/allure-report'
                allure([
                    includeProperties: true,
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'build/allure-results']]
                ])

                // Публикуем результаты JUnit
                junit '**/build/test-results/test/TEST-*.xml'

                // Теперь файл summary.json должен существовать
                if (fileExists('allure-report/widgets/summary.json')) {
                    def summary = readJSON file: 'allure-report/widgets/summary.json'

                    def total = summary.statistic.total
                    def passed = summary.statistic.passed
                    def failed = summary.statistic.failed
                    def broken = summary.statistic.broken
                    def skipped = summary.statistic.skipped

                    // Формируем текст сообщения
                    def messageText = """📊 *Результаты тестов*
🕒 Duration: ${currentBuild.durationString.replace('and counting', '')}
📌 Total: ${total}
✅ Passed: ${passed}
❌ Failed: ${failed}
💥 Broken: ${broken}
⚠️ Skipped: ${skipped}
🔗 [Allure Report](${env.BUILD_URL}allure)
"""

                    // Используем Jenkins Credentials для безопасного доступа к токену
                    withCredentials([string(credentialsId: 'TELEGRAM_BOT_TOKEN', variable: 'BOT_TOKEN'), string(credentialsId: 'TELEGRAM_CHAT_ID', variable: 'CHAT_ID')]) {

                        // Запускаем наш Java-код из созданного JAR-файла
                        sh "java -jar build/libs/chart-generator.jar ${total} ${passed} ${failed} ${broken} ${skipped} chart.png"

                        // Проверяем, что файл изображения был создан
                        if (fileExists('chart.png')) {
                            // Отправляем фото с подписью в Telegram
                            sh """
                                curl -s -X POST \
                                     -F "chat_id=${CHAT_ID}" \
                                     -F "photo=@chart.png" \
                                     -F "caption=${messageText}" \
                                     -F "parse_mode=Markdown" \
                                     "https://api.telegram.org/bot${BOT_TOKEN}/sendPhoto"
                            """
                        } else {
                            echo "Chart.png not found, skipping Telegram photo notification."
                        }
                    }
                } else {
                    echo "Summary.json not found, skipping Telegram notification."
                }

                def reportDir = 'build/reports/tests/test'
                archiveArtifacts artifacts: "${reportDir}/**", allowEmptyArchive: true
            }
        }
    }
}