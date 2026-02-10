FROM maven:3-openjdk-17-slim as build-image

LABEL author="maires"

WORKDIR /app

COPY pom.xml pom.xml

RUN mvn dependency:go-offline

COPY . .

RUN mvn package spring-boot:repackage -DskipTests


FROM eclipse-temurin:17-jre-jammy

COPY --from=build-image /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]