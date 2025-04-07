# Build stage
FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim
WORKDIR /app

VOLUME /tmp

# Копируем собранный jar из предыдущего stage
COPY --from=build /app/target/*.jar app.jar

# Запускаем .jar, указав локальный путь (мы уже в /app)
ENTRYPOINT ["java", "-jar", "app.jar"]

#
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#
## Копируем только собранный jar-файл
#COPY target/*.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
