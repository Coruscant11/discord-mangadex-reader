FROM maven:3.8.6-openjdk-18

MAINTAINER murasaki
WORKDIR /murasaki
COPY . .

RUN mvn clean install

ENTRYPOINT ["java", "-jar", "target/Murasaki-1.0-SNAPSHOT.jar"]