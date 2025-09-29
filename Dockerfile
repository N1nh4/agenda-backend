# Etapa 1: build com cache de dependências
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia apenas o pom.xml para cache de dependências
COPY pom.xml ./

# Baixa todas as dependências antes de copiar o código
RUN mvn dependency:go-offline -B

# Copia o restante do código
COPY src ./src

# Build do projeto sem testes
RUN mvn clean package -DskipTests -B

# Etapa 2: imagem final
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o jar do build
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
