FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/impacto-backend-1.0.0.jar app.jar
RUN mkdir -p /app/storage
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
