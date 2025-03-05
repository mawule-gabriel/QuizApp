package org.example;

import java.util.List;

public class QuestionBank {

    public static List<QuizApp.Question> getQuestions() {
        return List.of(
                new QuizApp.Question(1, "Which AWS service can be used to create hybrid cloud environments with on-premises data centers?",
                        List.of("AWS Direct Connect", "AWS VPC Peering", "AWS Lambda", "Amazon CloudWatch"), "AWS Direct Connect"),

                new QuizApp.Question(2, "What is the maximum size of an Amazon S3 object?",
                        List.of("5 TB", "10 TB", "100 GB", "50 GB"), "5 TB"),

                new QuizApp.Question(3, "Which of the following is a feature of Amazon Aurora?",
                        List.of("Auto-scaling", "Cross-region replication", "Multi-AZ deployments", "All of the above"), "All of the above"),

                new QuizApp.Question(4, "What does the AWS Well-Architected Framework focus on?",
                        List.of("Cost optimization", "Operational excellence", "Security", "All of the above"), "All of the above"),

                new QuizApp.Question(5, "Which AWS service provides a managed Kubernetes service?",
                        List.of("Amazon EKS", "Amazon ECS", "AWS Lambda", "AWS Fargate"), "Amazon EKS"),

                new QuizApp.Question(6, "Which of the following is a valid use case for AWS Transit Gateway?",
                        List.of("Connecting multiple VPCs", "Scaling EC2 instances", "Storing data in S3", "Running containerized applications"), "Connecting multiple VPCs"),

                new QuizApp.Question(7, "Which AWS service enables encryption for data in transit between Amazon EC2 instances?",
                        List.of("AWS Key Management Service (KMS)", "AWS Secrets Manager", "Elastic Load Balancing (ELB)", "AWS VPN"), "AWS Key Management Service (KMS)"),

                new QuizApp.Question(8, "What is the maximum number of IP addresses that can be allocated per VPC CIDR block?",
                        List.of("5,000", "4,096", "65,536", "1,000"), "65,536"),

                new QuizApp.Question(9, "Which AWS service is designed to automatically monitor and manage containerized applications in a multi-cloud environment?",
                        List.of("Amazon ECS", "AWS Fargate", "Amazon EKS", "AWS App Mesh"), "AWS App Mesh"),

                new QuizApp.Question(10, "What is the primary purpose of AWS Shield?",
                        List.of("Protecting against DDoS attacks", "Managing EC2 instances", "Optimizing S3 storage", "Network traffic monitoring"), "Protecting against DDoS attacks"),

                new QuizApp.Question(11, "Which AWS service allows you to implement Infrastructure as Code (IaC) with support for declarative templates?",
                        List.of("AWS CloudFormation", "AWS Elastic Beanstalk", "Amazon EC2", "AWS OpsWorks"), "AWS CloudFormation"),

                new QuizApp.Question(12, "What AWS service would you use for serverless deployment of applications with low latency?",
                        List.of("AWS Lambda", "AWS Fargate", "Amazon EC2", "Amazon API Gateway"), "AWS Lambda"),

                new QuizApp.Question(13, "Which of the following AWS services can be used to create a Virtual Private Cloud (VPC) within an AWS account?",
                        List.of("AWS VPC", "AWS Direct Connect", "Amazon S3", "AWS CloudTrail"), "AWS VPC"),

                new QuizApp.Question(14, "Which AWS service provides a fully managed, scalable DNS and domain name registration service?",
                        List.of("Amazon Route 53", "Amazon CloudFront", "AWS WAF", "Amazon VPC"), "Amazon Route 53"),

                new QuizApp.Question(15, "Which service would you use to orchestrate batch jobs and automate workflows within AWS?",
                        List.of("AWS Batch", "AWS Data Pipeline", "AWS Step Functions", "Amazon ECS"), "AWS Step Functions"),

                new QuizApp.Question(16, "What is the maximum duration of an AWS Lambda function?",
                        List.of("15 minutes", "5 minutes", "24 hours", "1 hour"), "15 minutes"),

                new QuizApp.Question(17, "Which of the following tools can be used to analyze and visualize logs from AWS CloudTrail and CloudWatch?",
                        List.of("Amazon Athena", "AWS QuickSight", "Amazon Kinesis", "All of the above"), "All of the above"),

                new QuizApp.Question(18, "Which AWS service is used to automate the deployment of infrastructure in a repeatable and consistent manner?",
                        List.of("AWS CloudFormation", "AWS Elastic Beanstalk", "Amazon EC2", "Amazon Lightsail"), "AWS CloudFormation"),

                new QuizApp.Question(19, "Which AWS service allows you to define and enforce security policies across your AWS resources?",
                        List.of("AWS Identity and Access Management (IAM)", "AWS Organizations", "AWS Key Management Service", "AWS WAF"), "AWS Organizations"),

                new QuizApp.Question(20, "What is the purpose of AWS Global Accelerator?",
                        List.of("Optimizing global application performance", "Automatically scaling EC2 instances", "Providing DDoS protection", "Managing DNS records"), "Optimizing global application performance")
        );
    }
}
