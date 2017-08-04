node {

    stage('Preparation') { // for display purposes
        // Get some code from a GitHub repository
        git credentialsId: 'change-me-please', url: 'git@github.com:praqma-training/gildedrose.git'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
    }

    stage('Build') {
        // Run the maven build
        if (isUnix()) {
            dir('Java') {
            sh "mvn -Dmaven.test.failure.ignore clean package"
            }
        }
    }

    stage('Results') {
        dir('Java') {
            junit '**/target/surefire-reports/TEST-*.xml'
            archive 'target/*.jar'
        }
    }
}
