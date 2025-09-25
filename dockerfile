FROM eclipse-temurin:17.0.8_7-jdk

COPY server/build/libs/*SNAPSHOT.jar onetool-server.jar

ENTRYPOINT ["java", "-jar", "onetool-server.jar"]
