FROM openjdk:8-jdk-alpine
# Add Author info
LABEL maintainer="minssan9@gmail.com"
# Add a volume to /tmp
VOLUME /fn-back
# The application's jar file
ARG JAR_FILE=build/libs/*.jar
# Add the application's jar to the container
ADD ${JAR_FILE} app.jar
# Run the jar file
ENTRYPOINT ["java", "-Dspring.profiles.active=api", "-jar", "/app.jar"]

