pipeline {
    agent any

    options {
        timestamps()
        ansiColor('xterm')
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['smoke', 'regression'],
            description: 'Что запускать'
        )
        booleanParam(
            name: 'CLEAN',
            defaultValue: true,
            description: 'Выполнять gradle clean перед тестами'
        )
    }

    environment {
        // На CI лучше без демона, чтобы логи были полными
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
                  ./gradlew ${params.CLEAN ? 'clean ' : ''}${params.TEST_SUITE}Test --no-daemon
                """
            }
        }
    }

    post {
        always {
            // JUnit отчёты (полезно для "Trends")
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
            // Allure (если плагин установлен)
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
            // Сохранить HTML-репорты Gradle
            archiveArtifacts artifacts: 'build/reports/tests/**', allowEmptyArchive: true
        }
    }
}