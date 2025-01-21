FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk
COPY --from=build /target/*.jar app.jar
EXPOSE 3000
ENTRYPOINT ["java","-jar","/app.jar"]
