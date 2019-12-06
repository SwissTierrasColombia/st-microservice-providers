FROM openjdk:12

VOLUME /tmp

ADD ./target/st-microservice-providers-0.0.1-SNAPSHOT.jar st-microservice-providers.jar

EXPOSE 8080

ENTRYPOINT java -jar /st-microservice-providers.jar