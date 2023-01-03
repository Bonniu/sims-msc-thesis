FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw depndency:go-offlinee

COPY src ./src
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]

