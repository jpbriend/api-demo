FROM maven:3.5-jdk-8-alpine

COPY . /usr/src/app
RUN mvn -f /usr/src/app/pom.xml install
RUN ls /usr/src/app/target
RUN mkdir /opt && cp /usr/src/app/target/api-demo-0.0.1-SNAPSHOT.jar /opt/app.jar

EXPOSE 8080
WORKDIR /opt

CMD ["java", "-jar", "app.jar"]