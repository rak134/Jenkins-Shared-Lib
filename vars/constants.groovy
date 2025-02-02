import groovy.transform.Field

@Field String AWS_ACCOUNT_ID = '120569619769'
@Field String AWS_DEFAULT_REGION = 'ap-south-1'
@Field String IMAGE_REPO_NAME = 'sample-webapp'
@Field String GIT_REPO_URL = 'https://github.com/rak134/devops-automation.git'

// Jenkins Credential IDs
@Field String GIT_CREDENTIALS_ID = 'github-credentials'
@Field String ECR_CREDENTIALS_ID = 'aws-ecr-credentials'

def call() {
    return this
}
