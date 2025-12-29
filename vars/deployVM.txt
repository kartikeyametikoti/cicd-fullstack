def call(Map cfg) {
    withCredentials([
        sshUserPrivateKey(
            credentialsId: 'deployment_server_credentials',
            keyFileVariable: 'KEYFILE'
        )
    ]) {
        sh """
            scp -o StrictHostKeyChecking=no -i "\$KEYFILE" \
            docker-compose.yaml ubuntu@${cfg.workerNode}:/home/ubuntu/
        """

        sh """
            ssh -o StrictHostKeyChecking=no -i \$KEYFILE ubuntu@${cfg.host} '
                cd /home/ubuntu
                env BACKEND_TAG=${env.image_tag} FRONTEND_TAG=${env.image_tag} docker compose pull
                env BACKEND_TAG=${env.image_tag} FRONTEND_TAG=${env.image_tag} docker compose up -d
            '
        """
    }
}
