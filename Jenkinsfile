pipeline {
    agent any

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression'],
            description: 'Выбери набор тестов для запуска'
        )
        booleanParam(
            name: 'CLEAN',
            defaultValue: true,
            description: 'Выполнять gradle clean перед тестами'
        )
    }

    environment {
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
        HEADLESS = 'true'
        BASE_URL = 'https://automationexercise.com'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Tests') {
            steps {
                sh 'chmod +x gradlew'
                sh """
                    ./gradlew ${params.CLEAN ? 'clean ' : ''}${params.TEST_SUITE}Test --no-daemon --info --continue
                """
            }
        }
    }

    post {
        always {
            // JUnit отчёты
            junit allowEmptyResults: true, testResults: "build/test-results/${params.TEST_SUITE}/**/*.xml"

            // Allure отчёты
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]

            // HTML отчёты Gradle
            archiveArtifacts artifacts: "build/reports/tests/${params.TEST_SUITE}/**", allowEmptyArchive: true
        }
    }
}