pipeline {
    agent any
    environment {
        LANG_TYPE = ""
        JDK_HOME = tool name: 'JDK 22', type: 'jdk'
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
                                sh 'docker-compose up -d'
                                withEnv(["JAVA_HOME=${JDK_HOME}"]) {
                                    sh 'chmod +x gradlew && ./gradlew build'
                                }
                                sh 'docker-compose down -v'
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
                            sh 'docker-compose up -d'
                            sh 'docker-compose run --rm test'
                            sh 'docker-compose down -v'
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