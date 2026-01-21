def call(Map config) {

    pipeline {
        agent {
            label config.agent ?: 'any'
        }

        stages {
            stage('Git Checkout') {
                steps {
                    echo "Starting Git checkout"
                    echo "Repository : ${config.repoUrl}"
                    echo "Branch     : ${config.branch ?: 'main'}"

                    checkout([
                        $class: 'GitSCM',
                        branches: [[
                            name: config.branch ?: 'main'
                        ]],
                        userRemoteConfigs: [[
                            url: config.repoUrl,
                            credentialsId: config.gitCreds
                        ]]
                    ])
                }
            }
        }
    }
}

