FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
# Replace with the actual name of your JAR
COPY --from=build /app/target/quarkus-app /app/

EXPOSE 7517
ENTRYPOINT ["java", "-jar", "app.jar"]
