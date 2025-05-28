def call() {
    def dockerImage = "my-docker-repo/my-app:1.0"
    echo "Building Docker image: ${dockerImage}"

    // Build Docker image
    sh "docker build -t ${dockerImage} ."

    // Push Docker image to repository
    withCredentials([string(credentialsId: 'docker-hub-credentials', variable: 'DOCKER_HUB_TOKEN')]) {
        sh """
            echo ${DOCKER_HUB_TOKEN} | docker login -u my-docker-username --password-stdin
            docker push ${dockerImage}
        """
    }
}
