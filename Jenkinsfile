node {
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git credentialsId: '520d2a48-92fa-484d-92db-962576ad1ce7', url: 'git@github.com:praqma-training/gildedrose.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         sh "mvn -Dmaven.test.failure.ignore clean package"
      }
   }
   stage('Results') {
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
}
