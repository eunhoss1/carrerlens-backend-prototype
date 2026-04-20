# CareerLens Backend

Spring Boot 기반 CareerLens 백엔드 프로젝트입니다.

## 실행
```bash
cd backend
./gradlew bootRun
```

## 빌드/테스트
```bash
cd backend
./gradlew clean build
./gradlew test
```

## 구조
- `src/main/java/com/careerlens/demo/careerlens`: 캡스톤 프로토타입 핵심 계층형 모듈
  - controller
  - service
  - repository
  - entity
  - dto
  - common
  - config
- `src/main/resources`: Spring 설정

## 데이터
seed 데이터는 루트 `/seed-data`를 기본으로 읽고,
필요 시 `/backend/seed-data` 경로도 지원합니다.
