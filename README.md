# Getting Started
Reference Documentation for 'Weather Service App' 

### Architecture and Technologies
Weather Service API is providing the rest end points for Current weather results and history of last 5 queries


All the requirements and validations from the assignment document has been satisfied.
Manual/Automatic input configuration has been made in the configuration files.

- Java 8
- Spring Boot
- Maven
- Mocito
- Swagger
- Docker

### How to Build and Run
Project is build using Maven and Run as jar

'java -jar weather-service-app-0.0.1-SNAPSHOT.jar'

### Documentation
Swagger has been configured with the service and API documentation can be viewed at below endpoints after starting the application.

`http://localhost:8090/swagger-ui.html#/`

Please check the 'weather-service.postman_collection.json' for sample rest end points 

## Deployment
Deployed the Weather service as Docker image. Please look Dockerfile

### Tools used for deployment
- AWS (EC2 instance)
- Docker
- GIT
- Jenkins
- Putty

### Steps to create CICD pipeline

1. Created EC2 instance in AWS (free subscription)
2. Launched the instance from putty 
3. Installed docker -> yum install docker
4. install git -> yum install git
5. docker pull jenkins/jenkins
6. Run docker jenkins docker image 
7. Open jenkins pipeline and created new maven project, provided all details like soure code, build command, post build script 
	to create and run docker image
	
Please look the 'Deployment-screenshots.docx' have some screen shots




