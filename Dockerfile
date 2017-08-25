FROM maven:onbuild
CMD ["java", "-jar", "target/api-demo-0.0.1-SNAPSHOT.jar"]