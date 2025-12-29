def call(Map cfg) {

    echo "Building Backend Image"
    sh """
        docker build \
        -t ${cfg.backendImage}:${env.image_tag} \
        ./backend
    """

    echo "Building Frontend Image"
    sh """
        docker build \
        --build-arg BACKEND_URL=http://${cfg.backendUrl}:5000 \
        -t ${cfg.frontendImage}:${env.image_tag} \
        ./frontend
    """

    echo "Pushing Backend Image"
    sh """
        docker push ${cfg.backendImage}:${env.image_tag}
    """

    echo "Pushing Frontend Image"
    sh """
        docker push ${cfg.frontendImage}:${env.image_tag}
    """
}
