def call(Map config) {

    if (!config.repoUrl) {
        error "repoUrl is required for git checkout"
    }

    if (!config.gitCreds) {
        error "gitCreds (credentialsId) is required for git checkout"
    }

    def branchName = config.branch ?: 'main'

    checkout([
        $class: 'GitSCM',
        branches: [[name: branchName]],
        userRemoteConfigs: [[
            url: config.repoUrl,
            credentialsId: config.gitCreds
        ]]
    ])

    script {
        env.GIT_COMMIT_ID = sh(
            script: 'git rev-parse --short HEAD',
            returnStdout: true
        ).trim()
    }

    echo "Checked out branch: ${branchName}"
    echo "Git commit ID: ${env.GIT_COMMIT_ID}"
}










// def call(Map config) {

//     checkout([
//         $class: 'GitSCM',
//         branches: [[name: config.branch ?: 'main']],
//         userRemoteConfigs: [[
//             url: config.repoUrl,
//             credentialsId: config.gitCreds
//         ]]
//     ])
// }
