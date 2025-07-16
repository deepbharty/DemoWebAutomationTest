pipeline {
    agent any

    parameters {
        string(name: 'browser', defaultValue: 'chrome', description: 'Browser to run tests on')
        string(name: 'environment', defaultValue: 'qa', description: 'Environment name')
        string(name: 'cucumberTags', defaultValue: '@Login', description: 'Tags to filter scenarios')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run Maven Tests with XML') {
            steps {
                bat """
                    mvn clean test -DsuiteXmlFile=src/test/java/runner/TestRunner.xml -Dbrowser=${params.browser} -Denvironment=${params.environment} -Dcucumber.filter.tags="${params.cucumberTags}"
                """
            }
        }

        stage('Publish Reports') {
            steps {
                script {
                    // ✅ Publish Jenkins Extent Report
                    publishHTML([
                        reportDir: 'target/ExtentReport',
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Extent Report',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])

                    // ✅ Publish Cucumber HTML Report
                    publishHTML([
                        reportDir: 'target/cucumber-reports',
                        reportFiles: 'htmlReport.html',
                        reportName: 'Cucumber HTML Report',
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline execution completed. Check reports in Jenkins."

            // Optional: Archive for download if needed
            archiveArtifacts artifacts: 'target/ExtentReport/*.html', allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/cucumber-reports/*.html', allowEmptyArchive: true
        }
    }
}
