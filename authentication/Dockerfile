# Use an official Maven image to build the application
FROM maven:3.9.7-amazoncorretto-21 AS build


# Set the working directory inside the container
WORKDIR /app

# Copy the project files to the working directory
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the application
FROM amazoncorretto:21-alpine-jdk

# Create a directory for the application
RUN mkdir /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port the application will run on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]