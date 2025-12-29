def call(Map cfg) {
    withCredentials([
        usernamePassword(
            credentialsId: cfg.credsId,
            usernameVariable: 'USR',
            passwordVariable: 'PWD'
        )
    ]) {
        sh """
            echo \$PWD | docker login ${cfg.registry} \
            -u \$USR --password-stdin
        """
    }
}
