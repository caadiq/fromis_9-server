pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'develop', url: 'https://github.com/caadiq/fromis_9-server'
            }
        }

         stage('Copy .env') {
            steps {
                sh 'cp /var/jenkins_home/server/env/.env.fromis_9 /var/jenkins_home/workspace/fromis_9/.env'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'chmod +x ./gradlew'
                sh './gradlew clean build -x test'
            }
        }

       stage('Copy JAR') {
           steps {
               sh '''
                   docker exec fromis_9-springboot rm -f /app/fromis_9.jar
                   docker cp /var/jenkins_home/workspace/fromis_9/build/libs/fromis_9-1.0.0.jar fromis_9-springboot:/app/fromis_9.jar
               '''
           }
       }

        stage('Restart Container') {
            steps {
                sh 'docker restart fromis_9-springboot'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}