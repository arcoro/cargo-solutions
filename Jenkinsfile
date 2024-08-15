pipeline {
    agent any

    enviroment{
        LANG_TYPE = ""
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

            stages {
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

                stage('Build') {
                    steps {
                        script {
                            if (LANG_TYPE == 'java') {
                                sh 'chmod +x gradlew && ./gradlew build'
                            } else if (LANG_TYPE == 'nodejs') {
                                sh 'npm install'
                            } else if (LANG_TYPE == 'python') {
                                sh 'pip install -r requirements.txt'
                            }
                        }
                    }
                }

                stage('SonarQube analysis') {
                    steps {
                        script {
                            def scannerHome = tool 'SonarQube Scanner 6.1';
                            withSonarQubeEnv('SonarQube') {
                                sh "${scannerHome}/bin/sonar-scanner"
                            }
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