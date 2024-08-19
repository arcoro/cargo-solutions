pipeline {
    agent any
    environment {
        LANG_TYPE = ""
        DOCKER_IMAGE = 'cargo-solutions'
    }

    stages {
        stage('Conditional Execution') {
            when {
                anyOf {
                    branch 'master'
                    branch 'feature/*'
                    branch 'develop'
                }
            }

            stage('Checkout') {
                steps {
                    echo 'Checking out code...'
                    checkout scm
                }
            }

            stage('Preparation') {
                steps {
                    script {
                        if (fileExists('build.gradle')) {
                            LANG_TYPE = 'java'
                        } else if (fileExists('package.json')) {
                            LANG_TYPE = 'nodejs'
                        } else if (fileExists('requirements.txt')) {
                            LANG_TYPE = 'python'
                        } else {
                            error "No se pudo detectar el lenguaje"
                        }
                    }
                }
            }

            stage('Build Docker Image') {
                steps {
                    echo 'Building Docker image...'
                    sh 'docker build -t ${DOCKER_IMAGE} .'
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        def scannerHome = tool 'SonarQube Scanner 6.1';
                        withSonarQubeEnv('SonarQube') {
                            sh '''
                                docker run --rm -v $WORKSPACE:/usr/src --workdir /usr/src ${DOCKER_IMAGE} ${scannerHome}/bin/sonar-scanner
                            '''
                        }
                    }
                }
            }

            stage('Test') {
                steps {
                    echo 'Running tests...'
                    script {
                        sh 'docker-compose up -d'
                        sh 'docker-compose run --rm test'
                        sh 'docker-compose down -v'
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
