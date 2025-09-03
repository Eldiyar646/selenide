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
                sh """
                    ./gradlew ${params.CLEAN ? 'clean ' : ''}${params.TEST_SUITE}Test \
                        --no-daemon --info --continue
                """
            }
        }
    }

    post {
        always {
            // JUnit XML отчёты
            junit allowEmptyResults: true, testResults: "build/test-results/${params.TEST_SUITE}Test/*.xml"

            // Allure отчёт
            allure([
                includeProperties: false,
                jdk: '',
                results: [[path: 'build/allure-results']]
            ])

            // HTML отчёт Gradle
            archiveArtifacts artifacts: "build/reports/tests/${params.TEST_SUITE}Test/**", allowEmptyArchive: true
        }
    }
}