pipeline {
    agent any


    stages {
        stage('pull code') {
            steps {
                echo 'pull code start'
                gitLabConnection('SEIIIGitlab')
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '4bc3f99c-5a7b-4406-970d-202fc70d7cba', url: 'https://git.nju.edu.cn/191250131/backend-511.git']]])
                //checkout([$class: 'GitSCM', branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[credentialsId: '4bc3f99c-5a7b-4406-970d-202fc70d7cba', url: 'http://172.29.4.49/191250128_511/backend-511.git']]])
            }
        }
        stage('build') {
            steps {
                echo 'build start'
                updateGitlabCommitStatus name: 'build', state: 'running'
                sh 'mvn clean package -Dmaven.test.skip=true'
                sh 'mvn clean test org.jacoco:jacoco-maven-plugin:0.7.3.201502191951:prepare-agent install -Dmaven.test.failure.ignore=true'
                jacoco()
            }
        }
        stage('run') {
            steps {
                echo 'run start'
                sh 'docker build -f Dockerfile -t backend_511 .'
                sh 'chmod u+x deploy.sh'
                sh './deploy.sh'
                sh 'docker run -d -p 8081:8081 --name backend_511 backend_511'
                //sh 'cp -r file/. /file'
                //sh 'docker stop demo'
                //sh 'docker rm demo'
                //sh 'docker run -d -v /file:/file -v /var/lib/jenkins/workspace/backend-511/target:/jar -p 8081:8081 --name demo  openjdk java -jar  -Duser.timezone=GMT+08  /jar/backend_511-0.0.1.jar'
                //sh 'java -jar /var/lib/jenkins/workspace/backend-511/target/backend_511-0.0.1.jar'
                // gitlabCommitStatus(connection: gitLabConnection(gitLabConnection: 'SEIIIGitlab', jobCredentialId: ''), name: 'success') {
                //     // some block
                // }
            }
            post {
                success {
                  updateGitlabCommitStatus name: 'build', state: 'success'
                }
                failure {
                  updateGitlabCommitStatus name: 'build', state: 'failed'
                }
              }
        }
    }
}

