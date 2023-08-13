FROM eclipse-temurin:19-jre-focal
MAINTAINER ITAcademy

ADD ./target/gateway-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/gateway-service.jar"]

EXPOSE 8080 8089