FROM eclipse-temurin:19-jre-focal
MAINTAINER ITAcademy

ADD ./target/report-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/report-service.jar"]

EXPOSE 8080 8089