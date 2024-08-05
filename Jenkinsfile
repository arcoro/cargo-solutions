pipeline {
    agent any

    stages {
        stage('Checkout -1') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    docker.build('cargo-solutions')
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    docker.image('cargo-solutions').inside {
                        sh 'docker-compose run --rm test'
                    }
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