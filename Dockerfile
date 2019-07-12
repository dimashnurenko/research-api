FROM maven

WORKDIR /app
COPY pom.xml .
RUN mvn package

COPY . .
RUN mvn package

CMD ["java", "-jar", "target/api-core-1.0-SNAPSHOT.jar"]
