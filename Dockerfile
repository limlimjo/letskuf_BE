# Java 17이 설치된 Docker 이미지 기반 사용
FROM eclipse-temurin:17-jre

# 컨테이너 안에 /app을 작업 디렉터리로 사용
WORKDIR /app

# Maven 빌드 결과물인 같은 JAR 파일을 컨테이너의 /app/app.jar로 복사함
COPY target/*.jar app.jar

# 8080 포트
EXPOSE 8080

# 컨테이너가 시작되면 SpringBoot 실행
ENTRYPOINT ["java", "-jar", "app.jar"]