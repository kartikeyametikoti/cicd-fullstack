def call(Map cfg) {
    sh "docker build -t ${cfg.backendImage}:${env.image_tag} ./backend"

    sh """
        docker build \
        --build-arg BACKEND_URL=http://${cfg.backendUrl}:5000 \
        -t ${cfg.frontendImage}:${env.image_tag} ./frontend
    """
}
