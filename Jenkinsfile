pipeline {
    agent any

    stages {
        stage('Conditional Execution') {
            when {
                anyOf {
                    branch 'master'
                    branch 'feature/*'
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

                stage('SonarQube ') {
                    steps {
                        echo 'Running SonarQube run...'
                        script {
                            sh 'chmod +x gradlew && ./gradlew sonarqube'
                        }
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