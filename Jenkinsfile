pipeline {
    agent any

    stages {

        stage('Start Minikube (if not running)') {
            steps {
                bat '''
                for /f %%i in ('minikube status --format "{{.Host}}"') do (
                    if /I not %%i==Running (
                        echo Minikube not running. Starting now...
                        minikube start
                    ) else (
                        echo Minikube is already running.
                    )
                )
                '''
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t aysenurdocker/swe304-4:latest .'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
                    '''
                }
            }
        }

        stage('Docker Push') {
            steps {
                bat 'docker push aysenurdocker/swe304-4:latest'
            }
        }

        stage('K8s Deploy') {
            steps {
                bat 'kubectl --kubeconfig=C:\\ProgramData\\Jenkins\\.kube\\config apply -f k8s\\deployment.yaml'
                bat 'kubectl --kubeconfig=C:\\ProgramData\\Jenkins\\.kube\\config apply -f k8s\\service.yaml'
            }
        }

    }

    triggers {
        pollSCM('* * * * *')
    }
}
