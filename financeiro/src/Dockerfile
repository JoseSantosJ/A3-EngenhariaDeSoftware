# Stage 1: Compilar o código
FROM ubuntu:latest AS build

RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven

WORKDIR /app
COPY . .

RUN mvn clean install

# Stage 2: Criar a imagem final
FROM openjdk:17-jdk-slim

EXPOSE 8080

COPY --from=build /app/target/financeiro-1.0.0.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
