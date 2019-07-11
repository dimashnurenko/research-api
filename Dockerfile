FROM openjdk

WORKDIR /app

COPY ./target/api-core-1.0-SNAPSHOT.jar .

CMD ["java", "-jar", "api-core-1.0-SNAPSHOT.jar"]
