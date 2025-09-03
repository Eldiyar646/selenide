pipeline {
    agent any

    options {
        timestamps()
        ansiColor('xterm')
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['Smoke', 'Regression'],
            description: 'Выбери какой набор тестов запускать'
        )
        booleanParam(
            name: 'CLEAN',
            defaultValue: true,
            description: 'Выполнять gradle clean перед тестами'
        )
    }

    environment {
        // чтобы логи были полными
        GRADLE_OPTS = '-Dorg.gradle.daemon=false'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Tests') {
            steps {
                sh """
                  chmod +x gradlew
                  ./gradlew ${params.CLEAN ? 'clean ' : ''}${params.TEST_SUITE}Test --no-daemon
                """
            }
        }
    }

    post {
        always {
            // JUnit отчёты
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
            // Allure (если установлен плагин)
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
            // HTML-репорты Gradle
            archiveArtifacts artifacts: 'build/reports/tests/**', allowEmptyArchive: true
        }
    }
}