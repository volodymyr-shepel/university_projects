import json
import os
import boto3
from botocore.exceptions import ClientError

# Initialize the SNS client
sns_client = boto3.client('sns')

def lambda_handler(event, context):
    # Get the SNS topic ARN from environment variables
    sns_topic_arn = os.environ.get('SNS_TOPIC_ARN')
    
    # Extract the user email from the Cognito event
    try:
        email = event['request']['userAttributes']['email']
    except KeyError:
        print("Email not found in user attributes.")
        return {
            'statusCode': 400,
            'body': json.dumps({'message': 'Invalid request. Email not found in user attributes.'})
        }

    # Subscribe the email to the SNS topic
    try:
        response = sns_client.subscribe(
            TopicArn=sns_topic_arn,
            Protocol='email',
            Endpoint=email
        )
        print(f"Subscription successful for email: {email}. SubscriptionArn: {response['SubscriptionArn']}")
    except ClientError as e:
        print(f"Failed to subscribe email to SNS topic. Error: {e}")
        return {
            'statusCode': 500,
            'body': json.dumps({'message': 'Failed to subscribe email to SNS topic.', 'error': str(e)})
        }

    # Return the event to indicate successful execution
    return event
