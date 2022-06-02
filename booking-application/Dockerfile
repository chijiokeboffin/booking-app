FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} booking.jar
ENTRYPOINT ["java", "-jar", "booking.jar"]