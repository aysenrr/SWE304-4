pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/aysenrr/SWE304-4.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t aysenurdocker/swe304-4:latest .'
            }
        }
        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }
        stage('Docker Push') {
            steps {
                sh 'docker push aysenurdocker/swe304-4:latest'
            }
        }
        stage('K8s Deploy') {
            steps {
                sh 'kubectl apply -f k8s/deployment.yaml'
                sh 'kubectl apply -f k8s/service.yaml'
            }
        }
    }
    triggers {
        pollSCM('* * * * *') // Her 1 dakikada bir GitHub push var mı diye kontrol eder
    }
} 