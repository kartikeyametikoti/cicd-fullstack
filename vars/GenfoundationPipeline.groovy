def call(Map cfg = [:]) {

    pipeline {
        agent { label cfg.agent ?: 'any' }

        options {
            timestamps()
        }

        stages {

            stage('Git Checkout') {
                steps {
                    gitCheckout(
                        repoUrl: cfg.repoUrl ?: '',
                        branch: cfg.branch ?: 'main',
                        gitCreds: cfg.gitCreds ?: ''
                    )
                }
            }

            stage('Docker Build') {
                steps {
                    dockerBuild(
                        backendImage: cfg.backendImage ?: 'backend-app',
                        frontendImage: cfg.frontendImage ?: 'frontend-app',
                        backendUrl: cfg.backendUrl ?: 'localhost'
                    )
                }
            }
        }

        post {
            success {
                echo "Pipeline completed successfully for commit ${env.GIT_COMMIT_ID}"
            }
            failure {
                echo "Pipeline failed"
            }
        }
    }
}











// def call(Map config) {

//     pipeline {
//         agent {
//             label config.agent ?: 'any'
//         }

//         stages {
//             stage('Git Checkout') {
//                 steps {
//                     echo "Starting Git checkout"
//                     echo "Repository : ${config.repoUrl}"
//                     echo "Branch     : ${config.branch ?: 'main'}"

//                     checkout([
//                         $class: 'GitSCM',
//                         branches: [[
//                             name: config.branch ?: 'main'
//                         ]],
//                         userRemoteConfigs: [[
//                             url: config.repoUrl,
//                             credentialsId: config.gitCreds
//                         ]]
//                     ])
//                 }
//             }
//         }
//     }
// }

