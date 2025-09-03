pipeline {
    agent any

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
                // оборачиваем шаг в ansiColor
                ansiColor('xterm') {
                    sh """
                      chmod +x gradlew
                      ./gradlew ${params.CLEAN ? 'clean ' : ''}${params.TEST_SUITE}Test --no-daemon
                    """
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
            archiveArtifacts artifacts: 'build/reports/tests/**', allowEmptyArchive: true
        }
    }
}