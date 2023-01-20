FROM openjdk:19-jdk-alpine

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src
COPY logs ./logs
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]

