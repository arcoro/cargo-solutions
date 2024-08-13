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

                stage('SonarQube analysis') {
                    steps {
                        script {
                            sh 'chmod +x gradlew && ./gradlew build'
                        }
                        script {
                            def scannerHome = tool 'SonarQube Scanner 6.1';
                            withSonarQubeEnv('SonarQube') {
                                sh "${scannerHome}/bin/sonar-scanner"
                            }
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