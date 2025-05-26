def call() {
    def ec2Ip = '13.51.85.169'
    echo "Deploying application to EC2 instance: ${ec2Ip}"
    
    // Use the Secret File stored in Jenkins credentials
    withCredentials([file(credentialsId: 'ssh-key', variable: 'SSH_KEY_PATH')]) {
        // Debug: Print SSH Key Path
        sh "echo 'Using SSH Key Path: ${SSH_KEY_PATH}'"
        
        // Debug: Verify the JAR file exists
        sh "ls -l target/sample-java-app-1.0-SNAPSHOT.jar"
        
        // Transfer the JAR file to the EC2 instance
        sh """
            scp -o StrictHostKeyChecking=no -i ${SSH_KEY_PATH} target/sample-java-app-1.0-SNAPSHOT.jar ubuntu@${ec2Ip}:/home/ubuntu/
        """
        
        // Run the JAR file on the EC2 instance
        sh """
            ssh -o StrictHostKeyChecking=no -i ${SSH_KEY_PATH} ubuntu@${ec2Ip} "java -jar /home/ubuntu/sample-java-app-1.0-SNAPSHOT.jar"
        """
    }
}
