# -------- STAGE 1: Build the JAR --------
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Install Maven inside the container
RUN apt-get update && apt-get install -y maven

# Copy project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# -------- STAGE 2: Run the app --------
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
