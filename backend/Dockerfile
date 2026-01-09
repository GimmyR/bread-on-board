# Stage 1: Build the application using a Maven image with Temurin JDK
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application using a smaller runtime-only image (JRE or custom jlink image)
FROM eclipse-temurin:21-jre
WORKDIR /opt/app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]