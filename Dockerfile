FROM maven as package

WORKDIR /app

COPY ./pom.xml ./pom.xml
RUN mvn clean install

COPY ./src ./src
RUN mvn package

#FROM openjdk:11
#
#WORKDIR /app
#
#COPY --from=package /app/target/api-core-1.0-SNAPSHOT.jar ./target/api-core-1.0-SNAPSHOT.jar

CMD ["java", "-jar", "/app/target/api-core-1.0-SNAPSHOT.jar"]
