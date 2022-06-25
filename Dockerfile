FROM maven:3.8.6-openjdk-18

MAINTAINER murasaki
WORKDIR /murasaki
COPY . .

RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

FROM openjdk:18-jre-alpine
ENTRYPOINT ["java", "-jar", "target/Murasaki-1.0-SNAPSHOT.jar"]