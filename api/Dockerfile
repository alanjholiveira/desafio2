FROM gradle:jdk11-alpine AS BUILDER

MAINTAINER  Alan Oliveira

CMD mkdir /builder

WORKDIR /builder

COPY ./ /builder/

RUN ./gradlew build --exclude-task test

FROM openjdk:11-jre-slim
WORKDIR /root/
COPY --from=BUILDER /builder/build/libs ./
EXPOSE 8080

ENTRYPOINT ["java","-jar","./desafio-docker-0.0.1-SNAPSHOT.jar"]
