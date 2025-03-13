# Quiz App

## Overview

This project is a simple quiz application that runs on an embedded HTTP server using pure Java. The application serves a set of quiz questions and provides users with a score at the end.

In addition to building the quiz application, this project demonstrates how to **containerize** a Java application, **build a Docker image**, and **push it to Amazon Elastic Container Registry (ECR)** using a **GitHub Actions pipeline**.

## Objective

The main objective of this project is to:
- Containerize a Java quiz application using Docker.
- Build the Docker image using a GitHub Actions pipeline.
- Push the Docker image to a public Amazon Elastic Container Registry (ECR) repository.
- Deploy the containerized application on **Amazon ECS Fargate** and set up **Application Load Balancer** for routing traffic.

## Features

- **Quiz Application**: Users can answer multiple-choice questions, and the app will provide feedback on correct or incorrect answers and show the final score.
- **Dockerized**: The Java-based application is containerized into a Docker image.
- **CI/CD Pipeline**: A GitHub Actions pipeline automatically builds and pushes the Docker image to Amazon ECR when changes are made to the repository.
- **ECS Deployment**: The application is deployed on Amazon ECS Fargate with an Application Load Balancer to distribute traffic to the service.

## ECS Fargate Deployment Steps

As part of the deployment process, I have completed the following tasks:

1. **Used Docker Image from Amazon ECR**:  
   The containerized Java application was uploaded to Amazon Elastic Container Registry (ECR) in a previous step, and this image is now used for the ECS deployment.

2. **Created ECS Task Definition and Service**:  
   I created an ECS task definition that references the Docker image from Amazon ECR. This task definition also specifies autoscaling options for the service to handle varying amounts of traffic.

3. **Configured Application Load Balancer**:  
   An Application Load Balancer (ALB) was set up to route incoming traffic to the ECS service. This ensures that traffic is evenly distributed across the tasks running in the ECS service, providing high availability and scalability.

## Requirements

- Docker
- Java 8 or higher
- AWS Account (for ECR and ECS Fargate)
- GitHub repository
- GitHub Actions for CI/CD pipeline
- AWS CLI for ECS setup
- Application Load Balancer setup on AWS
