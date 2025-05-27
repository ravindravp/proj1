def call() {
    def ec2Ip = '13.49.21.119'
    echo "Deploying application to EC2 instance: ${ec2Ip}"
    
    // Use the Secret File stored in Jenkins credentials
    withCredentials([file(credentialsId: 'ssh-key', variable: 'SSH_KEY_PATH')]) {
        try {
            // Verify the JAR file exists before attempting to transfer
            def jarFilePath = "target/sample-java-app-1.0-SNAPSHOT.jar"
            if (!fileExists(jarFilePath)) {
                error("JAR file not found at path: ${jarFilePath}. Ensure the build step has completed successfully.")
            }
            
            // Transfer the JAR file to the EC2 instance
            echo "Transferring JAR file to EC2 instance..."
            sh """
                scp -o StrictHostKeyChecking=no -i ${SSH_KEY_PATH} ${jarFilePath} ubuntu@${ec2Ip}:/home/ubuntu/
            """
            
            // Run the JAR file on the EC2 instance
            echo "Running JAR file on EC2 instance..."
            sh """
                ssh -o StrictHostKeyChecking=no -i ${SSH_KEY_PATH} ubuntu@${ec2Ip} "java -jar /home/ubuntu/sample-java-app-1.0-SNAPSHOT.jar --server.port=8081"
            """
            
            echo "Deployment completed successfully!"
        } catch (Exception e) {
            error("Deployment failed: ${e.message}")
        }
    }
}
