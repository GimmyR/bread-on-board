# IF YOU WANT TO BUILD THE APPLICATION BY USING DOCKER BEFORE RUNNING IT

# Stage 1:
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2:
FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y maven
WORKDIR /opt/app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]



# IF YOU WANT TO RUN YOUR JAR FILE BY USING DOCKER

#FROM eclipse-temurin:21-jre
#WORKDIR /opt/app
#COPY target/*.jar app.jar
#EXPOSE 8080
#CMD ["java", "-jar", "app.jar"]