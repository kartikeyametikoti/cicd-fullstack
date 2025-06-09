pipeline {
    agent any 
    environment{
        image_tag=credentials('image-tag')
        backend_image=credentials('backend-image-uri')
        frontend_image=credentials('frotnend-image-uri')
        Ecr_password=credentials('password')  
        ssh_ip="54.234.51.175"    
     }
    stages { 
        stage('checking out the code ') {
            when{
            branch 'main'
            }
            steps {
                git branch: 'main', credentialsId: '49a93094-22fe-41cc-ba8d-32d7cf42301d', url: 'https://github.com/kartikeyametikoti/cicd-fullstack'
            }
        }  
        stage('building image'){
            steps{
                sh 'docker build -t $backend_image:$image_tag ./backend'
                sh 'docker build --build-arg BACKEND_URL=http://$ssh_ip:5000 -t $frontend_image:$image_tag ./frontend'

        } 
    }
              
        stage('Login to ECR') {
            steps {
                withCredentials([usernamePassword(credentialsId: '7c3d66f0-128e-438b-87f9-513485b3ec40', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {
        sh '''
                aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $Ecr_password 
                '''
             }
            }
        }
        stage('push to ecr'){
            steps{
                sh 'docker push $backend_image:$image_tag'
                sh 'docker push $frontend_image:$image_tag'
        }
        }
        stage('Deploy to EC2') {
            // when{ 
            //     branch 'main'
            // }
           steps {
        withCredentials([string(credentialsId: 'image-tag', variable: 'image-tag'), string(credentialsId: 'backend-image-uri', variable: 'backend-image-uri'), string(credentialsId: 'frotnend-image-uri', variable: 'frontend-image-uri'), sshUserPrivateKey(credentialsId: 'd0e39f12-5b65-418a-8262-6a41e75e109e', keyFileVariable: 'ssh_key', usernameVariable: 'ssh_user')]) {
            sh """
            ssh -o StrictHostKeyChecking=no -i $ssh_key $ssh_user@$ssh_ip << EOF
            sudo apt update
            sudo apt upgrade -y
            sudo apt install docker.io -y || true
            sudo systemctl enable docker || true
            sudo systemctl start docker || true
            sudo usermod -aG docker ubuntu || true
            sudo docker network create kartik-network || true
            sudo docker stop backend || true
            sudo docker rm backend || true
            sudo docker stop frontend || true
            sudo docker rm frontend || true
            sudo docker rmi $backend_image:$image_tag || true
            sudo docker rmi $frontend_image:$image_tag || true
            sudo docker run -d --name mysql-container --network kartik-network --restart always -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=kartikdatabase  -e MYSQL_USER=kartikuser -e MYSQL_PASSWORD=password -p 3300:3306 mysql:latest || true
            aws ecr get-login-password --region us-east-1 | sudo docker login --username AWS --password-stdin $Ecr_password
            sudo docker pull $backend_image:$image_tag
            sudo docker pull $frontend_image:$image_tag
            sudo docker run --name=backend --network kartik-network --restart always -p 5000:5000 -e DB_HOST=mysql-container -e DB_PASSWORD=password -e DB_NAME=kartikdatabase  -e DB_USER=kartikuser -d $backend_image:$image_tag
            sudo docker run --name=frontend --restart always -p 3000:80 -d $frontend_image:$image_tag
            
            # if we want to send your container logs to cloudwatch then we can use this flag called --log -driver 
            # sudo docker run -d   --name backend   --log-driver=awslogs   --log-opt awslogs-region=us-east-1   --log-opt awslogs-group=/myapp/backend   --log-opt awslogs-create-group=true -e DB_HOST=mysql-container -e DB_PASSWORD=password -e DB_NAME=kartikdatabase  -e DB_USER=kartikuser -p 5000:5000 --network kartik-network 364700382033.dkr.ecr.us-east-1.amazonaws.com/backend-repo:latest
            
            sudo docker ps
EOF
            """
        }
    }
}
    }
}
 
