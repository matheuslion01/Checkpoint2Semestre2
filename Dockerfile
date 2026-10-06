# Etapa 1: build da aplicação
FROM maven:3.9.8-eclipse-temurin-17-alpine AS build
WORKDIR /opt/app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: execução da aplicação
FROM eclipse-temurin:17-alpine-3.23
WORKDIR /opt/app
COPY --from=build /opt/app/target/app.jar /opt/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
