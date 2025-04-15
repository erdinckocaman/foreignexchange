# !!! Run following instructions to build and run the Docker image
# mvn clean package
# docker build -t foreign-exchange-app .
# docker run -p 8080:8080 foreign-exchange-app

FROM openjdk:17-jdk-slim

WORKDIR /app
COPY target/foreign-exchange-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENV TZ=Europe/Istanbul

ENTRYPOINT ["java", "-jar", "app.jar"]