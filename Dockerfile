FROM maven:3.5-jdk-8-alpine as BUILD

COPY . /usr/src/app
RUN mvn -f /usr/src/app/pom.xml install

FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY --from=BUILD /usr/src/app/target/api-demo-0.0.1-SNAPSHOT.jar /opt/app.jar
WORKDIR /opt
CMD ["java", "-jar", "app.jar"]