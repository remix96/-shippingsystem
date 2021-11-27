FROM maven:3-jdk-11 as builder
WORKDIR /project
COPY entities/ entities
WORKDIR /project/entities
RUN mvn install
WORKDIR /project
COPY shipping .
RUN mvn package

FROM openjdk:15-alpine
WORKDIR /app
COPY --from=builder /project/target/shipping-0.0.1-SNAPSHOT.jar ./shipping.jar
CMD java -jar shipping.jar