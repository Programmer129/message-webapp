FROM java:8

VOLUME /tmp

EXPOSE 8080

ADD /target/message-webservice-0.0.1-SNAPSHOT.jar message-webservice-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","message-webservice-0.0.1-SNAPSHOT.jar"]