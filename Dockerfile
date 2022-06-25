FROM maven:3.8.6-openjdk-18 as maven

COPY . .

RUN mvn dependency:go-offline -B
RUN mvn clean package -DskipTests

FROM arm64v8/openjdk:20-slim

WORKDIR /murasaki
COPY --from=maven target/Murasaki-*.jar ./

ENTRYPOINT ["java", "-jar", "target/Murasaki-1.0-SNAPSHOT.jar"]