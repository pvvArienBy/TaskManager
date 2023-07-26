FROM eclipse-temurin:19-jre-focal
MAINTAINER ITAcademy

ADD ./target/user-service-1.3.1-SNAPSHOT.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/user-service-1.3.1-SNAPSHOT.jar"]

EXPOSE 8080 8089