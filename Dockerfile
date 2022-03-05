FROM java:8

ADD target/backend_511-0.0.1.jar /backend_511-0.0.1.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar","/backend_511-0.0.1.jar"]

MAINTAINER skyswords