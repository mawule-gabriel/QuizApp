# Quiz App

## Overview

This project is a simple quiz application that runs on an embedded HTTP server using pure Java. The application serves a set of quiz questions and provides users with a score at the end.

In addition to building the quiz application, this project demonstrates how to **containerize** a Java application, **build a Docker image**, and **push it to Amazon Elastic Container Registry (ECR)** using a **GitHub Actions pipeline**.

## Objective

The main objective of this project is to:
- Containerize a Java quiz application using Docker.
- Build the Docker image using a GitHub Actions pipeline.
- Push the Docker image to a public Amazon Elastic Container Registry (ECR) repository.

## Features

- **Quiz Application**: Users can answer multiple-choice questions, and the app will provide feedback on correct or incorrect answers and show the final score.
- **Dockerized**: The Java-based application is containerized into a Docker image.
- **CI/CD Pipeline**: A GitHub Actions pipeline automatically builds and pushes the Docker image to Amazon ECR when changes are made to the repository.

## Requirements

- Docker
- Java 8 or higher
- AWS Account (for ECR)
- GitHub repository
- GitHub Actions for CI/CD pipeline

