FROM amazoncorretto:21-alpine

#Copy project jar
WORKDIR /app
COPY target/*.jar app.jar


#Expose rest port
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]