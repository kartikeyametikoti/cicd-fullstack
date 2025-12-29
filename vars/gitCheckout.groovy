def call(Map cfg) {
    git branch: cfg.branch,
        credentialsId: cfg.credsId,
        url: cfg.repoUrl

    env.image_tag = sh(
        script: "git rev-parse --short HEAD",
        returnStdout: true
    ).trim()
}
