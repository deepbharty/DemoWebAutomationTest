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
                    publishHTML([
                        reportDir: 'reports',
                        reportFiles: 'ExtentReport.html',
                        reportName: 'Extent Report',
                        keepAll: true
                    ])

                    publishHTML([
                        reportDir: 'target/cucumber-reports',
                        reportFiles: 'htmlReport.html',
                        reportName: 'Cucumber HTML Report',
                        keepAll: true
                    ])
                }
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline execution completed. Check reports in Jenkins."
        }
    }
}
