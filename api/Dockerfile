FROM java:8

ADD ../target/api-0.0.1.jar /api-0.0.1.jar

ADD file /file

EXPOSE 8081

ENTRYPOINT ["java", "-jar","/api-0.0.1.jar"]

MAINTAINER skyswords