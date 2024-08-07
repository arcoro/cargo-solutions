pipeline {
    agent any

    stages {
        stage('Verify Docker Installation') {
            steps {
                script {
                    def dockerVersion = sh(script: 'docker --version', returnStdout: true).trim()
                    echo "Docker version: ${dockerVersion}"
                }
            }
        }
        stage('Verify Docker Compose Installation') {
            steps {
                script {
                    def composeVersion = sh(script: 'docker-compose --version', returnStdout: true).trim()
                    echo "Docker Compose version: ${composeVersion}"
                }
            }
        }

        stage('List Files') {
            steps {
                sh 'ls -la'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t cargo-solutions .'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh 'docker-compose run --rm test'
                }
            }
        }
    }

    post {
        always {
            script {
                sh 'docker-compose down -v'
            }
        }
    }
}