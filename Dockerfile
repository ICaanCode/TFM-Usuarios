# Fase 1: Construcción
FROM gradle:8.6-jdk21 AS build
WORKDIR /app
# Copiamos los archivos de configuración de Gradle y descargamos las dependencias
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon

# Copiamos el código fuente y construimos el proyecto
COPY src ./src
RUN gradle clean build -x test --no-daemon

# Fase 2: Creación de la imagen de JAR
FROM openjdk:21 AS jar
WORKDIR /app
# Copiamos el JAR generado desde la fase de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Fase 3: Ejecución
FROM openjdk:21
WORKDIR /app
# Copiamos el JAR del contenedor anterior
COPY --from=jar /app/app.jar app.jar
# Exponemos el puerto en el que correrá la aplicación
EXPOSE 8443
# Definimos el comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
