FROM java:8

ADD ../target/api.jar /api.jar

ADD file /file

EXPOSE 8081

ENTRYPOINT ["java", "-jar","/api.jar"]

MAINTAINER skyswords