import groovy.transform.Field

// Constants defined with @Field to make them globally accessible
@Field String GIT_URL = 'https://github.com/devops5014/sample-webapp.git'
@Field String PASSWORD = 'github-pass'

def call() {
    def mvnCommand = env.mvncommand ?: 'install'
    def deployEnv = env.DeploymentEnvironment ?: 'staging'
    
    pipeline {
        agent { label 'Slave' }
        
        stages {
            stage("Checkout Code") {
                steps {
                    script {
                        // Echoing the values for debugging purposes
                        echo "This is the URL from shared library: ${GIT_URL}"
                        echo "This is the password from shared library: ${PASSWORD}"
                        
                        // Checkout code using the GitSCM plugin
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/main']],
                            userRemoteConfigs: [[
                                url: GIT_URL,
                                credentialsId: PASSWORD
                            ]]
                        ])
                    }
                }
            }
            
            stage("Build") {
                steps {
                    script {
                        sh "mvn clean ${mvnCommand}"
                    }
                }
            }
            
            stage("Test") {
                steps {
                    sh 'mvn test'
                }
            }
            
            stage("Deploy To Artifactory") {
                steps {
                    configFileProvider([configFile(fileId: '8f023859-bc45-44cd-ae4a-e3462a3edcf3', variable: 'MAVEN_SETTINGS')]) {
                        sh 'mvn deploy -s $MAVEN_SETTINGS'
                    }
                }
            }
            
            stage("Deploy To Environment") {
                steps {
                    script {
                        sh "echo 'I am Deploying this war or application to ${deployEnv}'"
                    }
                }
            }
        }
    }
}

return this
