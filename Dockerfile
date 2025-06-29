# --- Build stage ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Tối ưu cache dependency
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy code sau để tận dụng cache tốt hơn
COPY src ./src

# Build ứng dụng
RUN mvn clean package -DskipTests

# --- Run stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Thêm non-root user
RUN useradd -ms /bin/bash appuser

COPY --from=build /app/target/*.jar app.jar
RUN chown appuser:appuser app.jar

EXPOSE 8081

# Chạy bằng non-root user
USER appuser

ENTRYPOINT ["java","-jar","app.jar"]
