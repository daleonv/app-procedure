# Usa la imagen distroless para Java 17
FROM gcr.io/distroless/java17-debian11

# Metadata
LABEL maintainer="Diego Leon <2diegoleon@gmail.com>"

# Configura la zona horaria
ENV TZ=America/Guayaquil

# Configura el encoding por defecto a UTF-8
ENV LANG C.UTF-8

# Expone el puerto en el que la aplicación escucha
EXPOSE 8082

# Establece el directorio de trabajo
WORKDIR /api

# Copia el archivo JAR generado al contenedor
COPY app-microservices-services/build/libs/*.jar /api/app.jar

# Definir la variable de entorno por defecto
ENV SPRING_PROFILES_ACTIVE=docker

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]