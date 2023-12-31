pipeline {
    agent any
    environment {
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
        IMAGE_NAME = readMavenPom().getArtifactId()
        IMAGE_VERSION = readMavenPom().getVersion()
        IMAGE_BASE="cherepakhin/$IMAGE_NAME"
        DOCKER_IMAGE_NAME="$IMAGE_BASE:$IMAGE_VERSION"
        DOCKER_IMAGE_NAME_LATEST="$IMAGE_BASE:latest"
        DOCKERFILE_NAME='Dockerfile'
    }

    stages {
        stage('Prepare') {
            agent {
                docker {
                    image 'adoptopenjdk/openjdk11'
                    args "-u 0:0 -w $PWD -v /home/vasi/.m2:/home/vasi/.m2 -v /var/run/docker.sock:/var/run/docker.sock"
                }
            }
            stages {
                stage('Build') {
                    steps {
                        checkout scm
                        sh './mvnw compile'
                    }
                }
                stage('Test') {
                    steps {
                        sh './mvnw test'
                        junit '**/target/surefire-reports/TEST-*.xml'
                    }
                }
                stage('Coverage') {
                    parallel {
                        stage('Sonar') {
                            steps {
                                withSonarQubeEnv('v.perm.ru') {
                                    // This expands the evironment variables SONAR_CONFIG_NAME, SONAR_HOST_URL, SONAR_AUTH_TOKEN that can be used by any script.
                                    sh "./mvnw sonar:sonar -Dsonar.projectKey=${IMAGE_NAME}"
                                }
                            }
                        }
                        stage('JaCoCo') {
                            steps {
                                jacoco(
                                        execPattern: 'target/*.exec',
                                        classPattern: 'target/classes',
                                        sourcePattern: 'src/main/java',
                                        exclusionPattern: 'src/test*'
                                )
                            }
                        }
                    }
                }
            }
        }
//        stage('Package develop') {
//            agent any
//            when {
//                branch 'develop'
//            }
//            steps {
//                sh 'helm version'
//                emailext body: "Ссылка на результат ${env.BUILD_URL} develop hook2",
//                        recipientProviders: [buildUser()],
//                        subject: "Сборка develop",
//                        attachLog: true,
//                        compressLog: true
//            }
//        }

//        stage('Package master') {
//            agent any
//            when {
//                branch 'master'
//            }
//            steps {
//                sh 'helm version'
//                sh './mvnw package -DskipTests'
//                script {
//                    def dockerImage = docker.build("${env.DOCKER_IMAGE_NAME}", "-f ${env.DOCKERFILE_NAME} .")
//                    docker.withRegistry('', 'docker_cherepakhin') {
//                        dockerImage.push()
//                        dockerImage.push("latest")
//                    }
//                    echo "Pushed Docker Image: ${env.DOCKER_IMAGE_NAME}"
//                }
//                sh "docker rmi ${env.DOCKER_IMAGE_NAME} ${env.DOCKER_IMAGE_NAME_LATEST}"
//            }
//            post {
//                success {
//                    archiveArtifacts 'target/*.jar'
//                    emailext body: "Ссылка на результат ${env.BUILD_URL}",
//                            recipientProviders: [buildUser()],
//                            subject: "Успешная сборка: ${currentBuild.fullDisplayName}",
//                            attachLog: true,
//                            compressLog: true
//                }
//            }
//        }

//        stage('Deploy to kubernetes') {
//            agent any
//            when {
//                branch 'master'
//            }
//            steps {
//                withKubeConfig([credentialsId: 'kuberid', serverUrl: "${KUBER_URL}", namespace: "${KUBER_NS_DEFAULT}"]) {
//                    sh "helm template ./helm --set image.tag=${env.IMAGE_VERSION} | kubectl apply -f -"
//                }
//            }
//        }
    }
}