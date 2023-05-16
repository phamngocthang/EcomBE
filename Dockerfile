FROM openjdk:17-jdk-alpine
COPY target/demo-api.jar demo-api.jar 
CMD ["java","-jar","demo-api.jar"]