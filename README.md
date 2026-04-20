# CareerLens Monorepo Prototype

캡스톤 시연용 CareerLens 프로젝트를 **프론트/백/문서/시드데이터**로 분리한 모노레포입니다.

## A. 전체 폴더 구조

```text
.
├── frontend/                  # Next.js + TypeScript + Tailwind
├── backend/                   # Spring Boot + JPA + MariaDB
│   ├── build.gradle
│   ├── settings.gradle
│   ├── gradlew
│   ├── src/
│   │   ├── main/java/com/careerlens/demo/careerlens/  # 핵심 도메인 계층형 구조
│   │   └── main/resources/
│   └── README.md
├── seed-data/                 # 사전 준비된 JSON seed 파일
└── docs/                      # 아키텍처/설계 문서
```


## 외부 공고 수집 제거 정책
- Greenhouse/ATS/스크래핑/자동 동기화 코드는 제거했습니다.
- 공고/패턴/표본 데이터는 관리자 수동 등록 또는 `seed-data` 주입만 사용합니다.
- 추천/진단은 `JobPosting + EmployeeProfileSample + PatternProfile` 중심 구조를 사용합니다.

## B. 백엔드 구조 설명
- 실제 Spring Boot 프로젝트는 `/backend` 아래로 완전히 이동했습니다.
- 계층은 `Controller → Service → Repository → Entity → DTO`를 유지합니다.
- 캡스톤 핵심 도메인은 `backend/src/main/java/com/careerlens/demo/careerlens`에 모아두었습니다.

## C. 프론트엔드 구조 설명
- `/frontend`는 Next.js App Router 기반 독립 실행 프로젝트입니다.
- 요구된 페이지(`/login`, `/profile`, `/jobs/recommendation`, `/planner`, `/applications`, `/settlement`, `/verification`, `/admin/verifications`, `/recruiter/verified-candidates`)를 유지합니다.

## D. 엔티티 목록
- User
- UserProfile
- JobPosting
- EmployeeProfileSample
- PatternProfile
- DiagnosisResult
- PlannerRoadmap
- PlannerTask
- ApplicationRecord
- SettlementChecklist
- VerificationRequest
- VerificationBadge

## E. API 목록
- Profile
  - `POST /api/profile`
  - `GET /api/profile/{user_id}`
- Recommendations
  - `POST /api/recommendations/diagnose`
  - `GET /api/recommendations/{user_id}`
- Planner
  - `POST /api/planner/generate`
  - `GET /api/planner/{user_id}`
  - `PATCH /api/planner/task/{task_id}`
- Applications
  - `POST /api/applications`
  - `GET /api/applications/{user_id}`
  - `PATCH /api/applications/{application_id}/status`
- Settlement
  - `GET /api/settlement/{country}`
  - `GET /api/settlement/{user_id}/checklist`
- Verification
  - `POST /api/verifications`
  - `GET /api/verifications/{user_id}`
  - `GET /api/recruiter/verified-candidates`
  - `PATCH /api/admin/verifications/{verification_id}`

## F. seed 데이터 구조
- `seed-data/seed_users.json`
- `seed-data/seed_jobs.json`
- `seed-data/seed_employee_profiles.json`
- `seed-data/seed_pattern_profiles.json`
- `seed-data/seed_verification_requests.json`
- `seed-data/seed_country_checklists.json`

## G. 화면 흐름 설명
1. 로그인
2. 프로필 입력
3. 추천 진단 결과 확인
4. 플래너 생성 및 체크
5. 기업 지원 칸반 관리
6. 합격 후 국가별 정착 체크리스트
7. 검증 프로필은 독립 신청, 관리자 승인, 채용자 열람

## H. 실행 방법

### 1) Backend 실행
```bash
cd backend
./gradlew bootRun
```

### 2) Frontend 실행
```bash
cd frontend
npm install
npm run dev
```

## I. 현재 MVP 구현 범위
- 시연 가능한 서비스 전체 흐름(추천→플래너→지원→정착)
- 검증 프로필 독립 흐름(신청→관리자 승인/반려→채용자 열람)
- 계층형 백엔드 구조 + seed 데이터 기반 데모
- 프론트/백 완전 분리 실행 구조

## J. 향후 확장 포인트
- 인증/인가(JWT, RBAC)
- 실제 ATS/API 연동
- 파일 업로드/검증 자동화
- 배포 자동화(CI/CD, Docker Compose)
