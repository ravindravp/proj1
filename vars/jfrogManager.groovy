def call() {
    def jarFilePath = "target/sample-java-app-1.0-SNAPSHOT.jar"
    def artifactoryRepo = "my-artifactory-repo"
    def artifactoryUrl = "https://<JFROG_ARTIFACTORY_URL>/artifactory/${artifactoryRepo}"

    echo "Uploading JAR file to JFrog Artifactory..."

    withCredentials([usernamePassword(credentialsId: 'jfrog-credentials', usernameVariable: 'JFROG_USER', passwordVariable: 'JFROG_PASSWORD')]) {
        sh """
            curl -u${JFROG_USER}:${JFROG_PASSWORD} -T ${jarFilePath} ${artifactoryUrl}/sample-java-app-1.0-SNAPSHOT.jar
        """
    }
}
