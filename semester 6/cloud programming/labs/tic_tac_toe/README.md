# Volodymyr Shepel - Cognito,S3,DynamoDB,SNS,Api Gateway, Lambda

- Course: *Cloud programming*
- Group: 
- Date: 06.10.2024

## Environment 
The Cloud Labs application leverages AWS services to manage user authentication, data storage, and notifications. Below is a detailed description of the architecture and workflow.

## Architecture Components

### User Authentication

1. **Amazon Cognito**: 
    - **Sign-Up and Verification**: 
        - Users sign up using the Amazon Cognito UI.
        - An email verification code is sent to the user.
        - Upon successful verification, a "successful sign-up" trigger in the user pool is activated.

2. **AWS Lambda**:
    - **User Subscription**: 
        - The successful sign-up trigger invokes a Lambda function.
        - This Lambda function subscribes the new user to an Amazon SNS topic for notifications.

### Data Management

1. **Amazon S3**:
    - Used for storing user profile images.
    
2. **Amazon DynamoDB**:
    - Stores user data, match history, and ranking information.
    - Backend interacts to retrieve data about match history and leader board.

### Game Results Processing

1. **Amazon API Gateway**:
    - **Endpoint Invocation**:
        - When a game finishes, the backend calls an API Gateway endpoint.
        - This endpoint invokes a POST method to handle the results.

2. **AWS Lambda**:
    - **Results Processing**:
        - The API Gateway triggers a Lambda function.
        - This Lambda function:
            - Updates the match history in DynamoDB.
            - Updates user rankings in DynamoDB.
            - Publishes a message to an Amazon SNS topic to notify all subscribers about the ranking update.

3. **Amazon SNS**:
    - **Notification**:
        - Subscribers to the SNS topic receive email notifications about ranking updates.

## Workflow Summary

1. User signs up via Cognito UI and verifies email.
2. Post-verification, a Lambda function subscribes the user to the SNS topic.
3. The backend interacts with S3 and DynamoDB using AWS credentials.
4. Upon game completion:
    - The frontend invokes an API Gateway endpoint.
    - A Lambda function processes the results, updates DynamoDB, and publishes an SNS message.
5. Subscribers receive email notifications about ranking updates.

## Reflections

- What did you learn?
- What obstacles did you overcome?
- What did you help most in overcoming obstacles?
- Was that something that surprised you?

### What did you learn?
During this project, I have gained extensive knowledge in utilizing various AWS services such as S3, DynamoDB, Lambda functions, API Gateway, and SNS topics. Integrating these services into my application has provided me with practical experience in cloud-based application development and deployment. I have also learned the following:
- Configuring S3 buckets for file storage and management.
- Setting up DynamoDB for data storage and retrieval.
- Writing and deploying Lambda functions for backend processing.
- Creating and managing REST APIs with API Gateway.
- Setting up SNS topics for user notifications and integrating them with other AWS services.
- Integrating these services in the application

### What obstacles did you overcome?
One significant obstacle I encountered was configuring the hosted UI for my instances, which required HTTPS, and also setting up backend communication which also requires HTTPS. To address this, I:
- Used self-generated certificates.
- Uploaded these certificates to AWS Certificate Manager via Terraform.
- Applied these certificates when creating the backend and frontend instances.

Another issue was setting up the Lambda function used for updating the database and processing results. This required integration with multiple services and managing permissions. By carefully reading the documentation and using a trial and error approach, I was able to achieve the goal.

A further challenge was subscribing users to the SNS topic. Initially, I configured this within the update result function, but it resulted in multiple subscription notifications, which was inefficient. To resolve this, I:
- Created another Lambda function specifically for subscribing users to the topic.
- Configured this Lambda function within the Cognito User Pool to trigger after successful account confirmation.

Integrating all of these services into the application was also challenging. Modifying the frontend, backend, and ensuring seamless integration was not particularly difficult, but it was very time-consuming due to the numerous details involved.

### What helped you most in overcoming obstacles?
Several resources were instrumental in overcoming these obstacles, including:
- AWS documentation, which provided detailed and comprehensive guides.
- Instructional videos that offered visual and step-by-step tutorials.
- ChatGPT, which assisted in troubleshooting and provided quick answers to specific questions.
- Patience and dedication in thoroughly understanding and applying new concepts.

### Was there something that surprised you?
The most surprising aspect of this project was discovering the vast array of AWS services available that can be seamlessly integrated into applications. Previously, when working on personal projects, I developed my own solutions for identity management, security, message queues, databases, and monitoring. Now, I understand the value of leveraging existing services, which can significantly reduce development time and complexity. This knowledge will undoubtedly be beneficial for future projects.

## Why I Chose DynamoDB
The main argument for choosing DynamoDB over RDS was based on several factors:


1. **Simplicity of Schema**: Our application does not require a complex database schema. In fact, it utilizes only two small tables, each with a minimal number of columns (2 and 3 columns respectively). DynamoDB is well-suited for simple, non-relational data models, making it an ideal choice for our needs.
   
2. **Integration with AWS Services**: DynamoDB integrates seamlessly with other AWS services such as Lambda, API Gateway, and SNS. This tight integration simplifies the process of building and maintaining our application’s infrastructure, enhancing overall efficiency and reliability.

3. **Ease of Integration**: DynamoDB was much easier to integrate into our application. Its fully managed nature reduces the overhead associated with database administration tasks such as patching, backup, and scaling, allowing us to focus more on application development rather than database management.
