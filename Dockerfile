# Usando a imagem oficial do Maven com JDK 22 para construir o projeto
FROM maven:3.9.7-eclipse-temurin-22 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia todos os arquivos do projeto para o container
COPY . .

# Lista os arquivos no diretório para debug
RUN ls -alh

# Verifica permissões e define permissões de execução para o Maven Wrapper
RUN chmod +x mvnw && ls -alh .mvn/wrapper

# Compila o projeto utilizando o Maven diretamente, ignorando os testes, com logging detalhado
RUN mvn -X clean package -DskipTests

# Usando a imagem oficial do OpenJDK 22 para rodar o projeto
FROM eclipse-temurin:22-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o artefato gerado na etapa de build para o diretório de trabalho do container
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Define o comando padrão para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
