# ---------- Stage 1: build the .jar ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Maven wrapper and project definition
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN chmod +x mvnw

# Source code, then build (tests skipped: they need the Render database)
COPY src src
RUN ./mvnw package -DskipTests -B

# ---------- Stage 2: run the .jar ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
