FROM arm64v8/maven:3.6.3-openjdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY checkstyle.xml /workspace
COPY src /workspace/src
RUN mvn initialize
RUN mvn -B package --file pom.xml -DskipTests

FROM adoptopenjdk/openjdk11:aarch64-ubuntu-jdk-11.0.10_9-slim
COPY --from=build /workspace/target/coingecko-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java","-jar","app.jar"]
