# 1단계: Gradle이 설치된 빌더 컨테이너에서 빌드
FROM gradle:8.4.0-jdk17 AS builder

WORKDIR /app
COPY . .
RUN cd server && chmod +x gradlew && ./gradlew clean build -x test

# 2단계: 실행만 담당하는 경량 OpenJDK 컨테이너
FROM openjdk:17-jdk AS runner

WORKDIR /app
# Gradle 프로젝트가 Maven과 달리 JAR은 build/libs에 생성됨
COPY --from=builder /app/server/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
