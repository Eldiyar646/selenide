pipeline {
    agent any

    environment {
        JAVA_HOME = "/usr/lib/jvm/java-21-openjdk-amd64"
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
        ALLURE_RESULTS = "${WORKSPACE}/build/allure-results"
        ALLURE_REPORT = "${WORKSPACE}/build/allure-report"
    }

    tools {
        // Указываем имя Allure, как оно задано в Jenkins Global Tool Configuration
        allure 'Allure'
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Prepare') {
            steps {
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Build & Test') {
            steps {
                sh './gradlew clean SmokeTest --no-daemon --info --continue'
            }
        }

        stage('Generate Allure Report') {
            steps {
                allure([
                    results: [[path: 'build/allure-results']],
                    reportBuildPolicy: 'ALWAYS',
                    includeProperties: false
                ])
            }
        }
    }

    post {
        always {
            junit '**/build/test-results/SmokeTest/*.xml'
        }
        success {
            echo "Tests passed!"
        }
        failure {
            echo "Some tests failed. Check Allure report for details."
        }
    }
}