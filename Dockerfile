# Base image
FROM maven:3.8.6-amazoncorretto-17 AS build

# Copy resource
COPY . /app

# Set working directory
WORKDIR /app

# Build application
RUN mvn clean package -DskipTests

# Run application with mysql
FROM amazoncorretto:17-alpine-jdk
COPY --from=build /app/target/SQLSampleApplication*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
