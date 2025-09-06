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

        stage('Generate Allure Report and Chart') {
            steps {
                script {
                    if (fileExists('build/allure-results')) {
                        // Генерация Allure отчёта
                        allure([
                            includeProperties: true,
                            reportBuildPolicy: 'ALWAYS',
                            results: [[path: 'build/allure-results']]
                        ])

                        // *** Новый шаг: Установка Puppeteer и создание скриншота ***
                        sh """
                            npm init -y
                            npm install puppeteer

                            node -e "
                              const puppeteer = require('puppeteer');
                              (async () => {
                                const browser = await puppeteer.launch({ headless: true, args: ['--no-sandbox', '--disable-setuid-sandbox'] });
                                const page = await browser.newPage();
                                await page.goto("file:///${env.WORKSPACE}/allure-report/index.html");
                                await page.setViewport({ width: 1000, height: 800 });
                                const chart = await page.$('.widgets');
                                if (chart) {
                                  await chart.screenshot({ path: 'chart.png' });
                                }
                                await browser.close();
                              })();
                            "
                        """
                        echo "Allure report chart saved as chart.png"
                    } else {
                        echo "Allure results folder not found, skipping Allure report and chart generation."
                    }
                }
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

                        def passedPercentage = (totalTests > 0) ? (int)((double) passedTests * 10000 / totalTests) / 100.0 : 0
                        def failedPercentage = (totalTests > 0) ? (int)((double) failedTests * 10000 / totalTests) / 100.0 : 0

                        def botToken = "8133371990:AAHoB2B54YGTPYMyv6khj4OYSc2MGs1mMi8"
                        def chatId = "8484572689"

                        def messageText = "Results:\nEnvironment: env\nComment: some comment\nDuration: ${currentBuild.durationString}\nTotal scenarios: ${totalTests}\nTotal passed: ${passedTests} (${passedPercentage}%)\nTotal failed: ${failedTests} (${failedPercentage}%)\nReport available at the link: ${env.BUILD_URL}allure"

                        // Теперь проверяем, что наш динамический файл chart.png существует
                        if (fileExists('chart.png')) {
                            sh """
                                curl -s -X POST \\
                                     -F "chat_id=${chatId}" \\
                                     -F "photo=@chart.png" \\
                                     -F "caption=${messageText}" \\
                                     "https://api.telegram.org/bot${botToken}/sendPhoto"
                            """
                        } else {
                            echo "Chart file chart.png not found, skipping photo upload to Telegram."
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