# Usando a imagem oficial do Maven com JDK 22 para construir o projeto
FROM maven:3.9.7-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Usando a imagem oficial do OpenJDK 22 para rodar o projeto
FROM eclipse-temurin:22-jdk
WORKDIR /app
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
