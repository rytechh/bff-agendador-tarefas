FROM maven:3.9-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar  app.jar

EXPOSE 8083

CMD ["java", "-jar", "app.jar"]