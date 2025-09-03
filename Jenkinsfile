pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression'],
            description: 'Выбери набор тестов: SmokeTest или RegressionTest'
        )
        booleanParam(
            name: 'CLEAN',
            defaultValue: true,
            description: 'Выполнять gradle clean перед тестами'
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
stage('Clean Build') {
    steps {
        sh 'rm -rf build'
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
                    echo "Запускаем Gradle задачу: ${taskName}"

                    // Выполняем Gradle и возвращаем статус
                    def status = sh(
                        script: "./gradlew ${params.CLEAN ? 'clean ' : ''}${taskName} --no-daemon --info --continue",
                        returnStatus: true
                    )

                    // Если есть ошибки тестов, помечаем билд как UNSTABLE
                    currentBuild.result = (status == 0) ? 'SUCCESS' : 'UNSTABLE'
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh './gradlew allureReport --no-daemon --info'
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
            // JUnit XML отчёты
            junit allowEmptyResults: true, testResults: "build/test-results/${params.TEST_SUITE}Test/*.xml"

            // Allure отчёт
            script {
                if (fileExists('build/allure-results')) {
                    allure([
                        includeProperties: true,
                        reportBuildPolicy: 'ALWAYS',
                        jdk: '',
                        results: [[path: 'build/allure-results']]
                    ])
                } else {
                    echo "Allure results folder not found, skipping Allure report."
                }
            }

            // HTML отчёт Gradle
            archiveArtifacts artifacts: "build/reports/tests/${params.TEST_SUITE}Test/**", allowEmptyArchive: true
        }
    }
}