# Etapa 1: Build
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Opcional: solo para documentación, Render no usa esto para enrutar
EXPOSE 8086

# No declares ENV PORT aquí, Render asignará la variable PORT

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
