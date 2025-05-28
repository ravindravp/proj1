def call() {
    def mvnHome = tool name: 'Maven', type: 'maven'
    withSonarQubeEnv('SonarQube') {
        sh "${mvnHome}/bin/mvn sonar:sonar -Dsonar.projectKey=my-project -Dsonar.host.url=http://<SONARQUBE_SERVER_URL> -Dsonar.login=<SONARQUBE_TOKEN>"
    }
}
