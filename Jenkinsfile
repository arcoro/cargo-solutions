pipeline {
    agent any

    stages {
        stage('Conditional Execution') {
            when {
                anyOf {
                    branch 'master'
                    branch 'develop'
                }
            }
            stages {
                stage('Checkout') {
                    steps {
                        echo 'Checking out code...'
                        checkout scm
                    }
                }

                stage('Build') {
                    steps {
                        echo 'Building the application...'
                        script {
                            sh 'docker build -t cargo-solutions .'
                        }
                    }
                }

                stage('Test') {
                    steps {
                        echo 'Running tests...'
                        script {
                            sh 'docker-compose run --rm test'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            script {
                sh 'docker-compose down -v'
            }
        }
    }
}
