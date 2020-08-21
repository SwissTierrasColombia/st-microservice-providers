FROM openjdk:12

ARG XMX=1024m
ARG PROFILE=production

ENV XMX=$XMX
ENV PROFILE=$PROFILE

VOLUME /tmp

ADD ./target/st-microservice-providers-0.0.1-SNAPSHOT.jar st-microservice-providers.jar

EXPOSE 8080

ENTRYPOINT java -Xmx$XMX -jar /st-microservice-providers.jar --spring.profiles.active=$PROFILE