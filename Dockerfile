FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

COPY . /app

RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21-jre-jammy

COPY --from=build /app/target/SistemaDePedidos-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]