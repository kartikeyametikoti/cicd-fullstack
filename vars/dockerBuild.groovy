def call(Map cfg = [:]) {

    // -------- Defaults --------
    def backendImage  = cfg.backendImage ?: 'backend-app'
    def frontendImage = cfg.frontendImage ?: 'frontend-app'
    def backendUrl    = cfg.backendUrl ?: 'localhost'

    // -------- Ensure commit ID is available --------
    def imageTag = env.GIT_COMMIT_ID ?: 'latest'

    echo "Using Docker image tag: ${imageTag}"

    // -------- Backend Build --------
    echo "Building Backend Docker Image: ${backendImage}:${imageTag}"
    sh """
        docker build \
        -t ${backendImage}:${imageTag} \
        backend
    """

    // -------- Frontend Build --------
    echo "Building Frontend Docker Image: ${frontendImage}:${imageTag}"
    sh """
        docker build \
        --build-arg BACKEND_URL=http://${backendUrl}:5000 \
        -t ${frontendImage}:${imageTag} \
        frontend
    """
}
