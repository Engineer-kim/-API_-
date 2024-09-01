# 빌드 단계
FROM gradle:7.6.0-jdk17 AS builder

WORKDIR /app

# Gradle Wrapper 및 설정 파일 복사
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# 줄바꿈 문자 변환 (윈도우에서 작업한 경우 필요할 수 있음)
RUN apt-get update && apt-get install -y dos2unix && dos2unix gradlew

# 실행 권한 부여
RUN chmod +x gradlew

# Gradle 의존성 다운로드
RUN ./gradlew dependencies --no-daemon

# 애플리케이션 소스 코드 복사
COPY src/ src/

# 빌드 실행
RUN ./gradlew bootJar --no-daemon --info

# 실행 단계
FROM openjdk:17-jdk-slim

# JAR 파일 복사
COPY --from=builder /app/build/libs/complete.jar /app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
