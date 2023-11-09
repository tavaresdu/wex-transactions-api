FROM gradle:8.4.0-jdk17-alpine AS builder
WORKDIR /opt/wex-transactions-api
COPY . .
RUN gradle clean bootJar

FROM eclipse-temurin:17.0.9_9-jre-alpine
COPY --from=builder /opt/wex-transactions-api/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]