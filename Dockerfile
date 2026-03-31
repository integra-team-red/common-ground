FROM gradle:8.14-jdk24 AS backend_build
WORKDIR /common-ground
COPY . .
RUN gradle assemble

FROM eclipse-temurin:24-jre
WORKDIR /common-ground
COPY --from=backend_build /common-ground/backend/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
