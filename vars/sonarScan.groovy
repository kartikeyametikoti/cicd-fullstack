def call(Map cfg) {
    withSonarQubeEnv('sonarqube') {
        def scannerHome = tool 'sonarqube'
        sh """
            ${scannerHome}/bin/sonar-scanner \
            -Dsonar.projectKey=${cfg.projectKey} \
            -Dsonar.projectName=${cfg.projectName} \
            -Dsonar.sources=${cfg.sources} \
            -Dsonar.exclusions=${cfg.exclusions}
        """
    }
}
