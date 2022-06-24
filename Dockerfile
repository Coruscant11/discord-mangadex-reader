FROM maven:3.8.6-openjdk-18

MAINTAINER murasaki
WORKDIR /murasaki
COPY . .

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
RUN mvn dependency:go-offline -B
RUN mvn clean install

ENTRYPOINT ["java", "-jar", "Murasaki-1.0-SNAPSHOT.jar"]