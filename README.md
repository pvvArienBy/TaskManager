
# TaskManager

The final project within the framework of the training course - Technologies for the development of enterprise solutions in JAVA from IT-ACADEMY.



## Project with microservice architecture

This project is a microservice architecture implemented in Docker containers.

## Description

A microservice architecture is an approach to application development in which an application is broken down into small, self-contained services that interact with each other through an API. Each service is responsible for a specific functionality and can be deployed and scaled independently of other services.

## Modules

The project consists of the following modules:

1. **User-service module**: Responsible for registering, authenticating users and issuing access tokens.

2. **Audit-service module**: Responsible for logging events throughout the project. Provides an API for handling request logging.

3. **task-service module**: Handles requests to create and manage projects and tasks.

4. **Report-service module**: Generates various reports based on data from other services.


## Components

The project includes the following components:

1. **Eureka**: Eureka is a registration and discovery service that allows services in your architecture to discover each other automatically.

2. **Spring Gateway**: Spring Gateway provides request proxying and routing between different services in your architecture.

3. **Minio**: Minio is an object storage system that provides a simple and scalable way to store files and objects in the cloud.

4. **Spring Security**: Spring Security provides authentication and authorization for your microservices, allowing you to control access to protected resources.

5. **Spring Data JPA**: Spring Data JPA provides a convenient way to interact with the database using an object-relational mapping (ORM).

6. **Spring MVC**: Spring MVC is a web application development framework that handles HTTP requests, including routing and data processing.

## Requirements

Containers require

1. Navigate to the root folder of the project where the Dockerfile and docker-compose.yml are located.

2. Build containers with `docker-compose build` command

3. Run containers with `docker-compose up` command

##Authors

- [@pvvArienBy](https://github.com/pvvArienBy)
