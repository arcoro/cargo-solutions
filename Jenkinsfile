pipeline {
    agent any

    stages {
       
        stage('Build') {
            steps {
                script {
                    sh 'docker build -t cargo-solutions .'
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