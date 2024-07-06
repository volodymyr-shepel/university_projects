# Volodymyr Shepel - Cognito

- Course: *Cloud Programming*
- Group: 
- Date: 06.10.2024

## Environment


# Environment Setup for Amazon Cognito

## Overview
This document outlines the steps taken to implement user authentication and registration functionality using Amazon Cognito Hosted UI within an AWS environment.

## SSL/TLS Configuration
To ensure secure communication via HTTPS, the following steps were undertaken:

1. **Certificate Generation**:
   - A private SSL/TLS certificate was generated to secure the application.

2. **Certificate Management**:
   - The certificate was uploaded to the AWS Certificate Manager (ACM) for streamlined management and deployment.

3. **Load Balancer Configuration**:
   - The Elastic Beanstalk environment's load balancer was configured to use the uploaded certificate, ensuring encrypted data transmission.

## Amazon Cognito Configuration
Configuration steps for Amazon Cognito included:

1. **Callback and Logout URLs**:
   - These URLs were configured using CNAME prefix to avoid circular dependency
   - The URLs were specified within the Amazon Cognito settings to handle authentication responses and user sign-out processes seamlessly.

By following these procedures, the user authentication and registration processes are secure, reliable, and compliant with best practices for HTTPS communication.


## Reflections

### What did you learn?
I have learned how to integrate AWS Cognito and its hosted UI into my application. Since using the hosted UI required HTTPS addresses, I gained a comprehensive understanding of SSL certificates, including their creation, integration into applications, and the setup of HTTPS traffic. This process enhanced my knowledge of secure communication over the internet.

### What obstacles did you overcome?
The biggest obstacle was setting up HTTPS. Although it was possible to bypass HTTPS by creating custom forms, the challenge of implementing HTTPS was more appealing to me. Utilizing the hosted UI is inherently more secure and facilitates easier integration with triggers, which were required in the subsequent task.

Additionally, I faced challenges with integrating Cognito into my application, particularly in setting correct callback and sign-out URLs. I overcame these issues by referring to the documentation and seeking help from the community on Stack Overflow.

Another significant hurdle was dealing with a perceived circular dependency. I needed to pass the client ID into my app, which is obtained after the creation of the user pool. Simultaneously, the callback and sign-out URLs for my app had to be set during the user pool creation. Initially, I attempted to resolve this using Elastic IP and EC2, but this method proved inefficient and impractical. Eventually, I found a better solution using Elastic Beanstalk and a CNAME prefix, guided by documentation and advice from colleagues.

### What helped most in overcoming obstacles?
The following resources were invaluable in overcoming these obstacles:

- **Documentation**: Detailed guides and official AWS documentation provided essential information and step-by-step instructions.
- **Videos**: Online tutorials and videos offered visual and practical demonstrations of the processes involved.
- **ChatGPT**: Quick and precise answers from ChatGPT helped clarify doubts and provided immediate solutions to specific questions.
- **Colleagues**: Advice and insights from colleagues were instrumental in navigating complex issues and finding efficient solutions.

### Was there something that surprised you?
Reflecting on the completed task, I am pleasantly surprised by the relative ease of integrating secure identity management into my application using AWS Cognito. Contrary to my previous experiences of building identity and access management (IAM) systems from scratch, which involved handling registration, sign-in, password recovery, access tokens, and refresh tokens, AWS Cognito offers a more secure, reliable, and time-efficient solution. Its seamless integration with other services and the simplicity of its setup make it a tool I will definitely use for future projects.
