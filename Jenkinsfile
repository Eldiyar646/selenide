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
            // Берем отчёты из папки соответствующей задачи
            junit allowEmptyResults: true, testResults: "build/test-results/${params.TEST_SUITE}/*.xml"
            // Allure
            allure includeProperties: false, jdk: '', results: [[path: 'build/allure-results']]
            // HTML
            archiveArtifacts artifacts: "build/reports/tests/${params.TEST_SUITE}/**", allowEmptyArchive: true
        }
    }
}