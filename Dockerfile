# Etapa 1: Build
FROM maven:3.8.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia archivos de configuración y código fuente
COPY pom.xml .
COPY src ./src

# Construye el proyecto y genera el JAR (sin tests para acelerar)
RUN mvn clean package -DskipTests

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia el JAR generado desde la etapa build
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto que usará la aplicación (Render usa variable PORT)
EXPOSE 8086

# Define variable de entorno PORT (opcional)
ENV PORT=8086

# Ejecuta la aplicación usando el puerto definido en Render
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=$PORT"]
