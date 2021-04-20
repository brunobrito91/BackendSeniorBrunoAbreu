FROM openjdk:11
COPY target/employee-registration-0.0.1-SNAPSHOT.jar employee-registration-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/employee-registration-0.0.1-SNAPSHOT.jar"]