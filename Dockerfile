FROM openjdk:17 AS build

WORKDIR /libraryapp
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17
WORKDIR /libraryapp
COPY --from=build /libraryapp/target/*.jar libraryapp.jar
ENTRYPOINT ["java", "-jar", "libraryapp.jar"]