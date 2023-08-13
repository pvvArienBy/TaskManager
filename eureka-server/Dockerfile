FROM eclipse-temurin:19-jre-focal
MAINTAINER ITAcademy

ADD ./target/eureka-server.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/eureka-server.jar"]

EXPOSE 8761