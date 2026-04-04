# CareerLens Backend Prototype

해외 취업 준비자를 위한 맞춤형 공고 추천 백엔드 프로토타입입니다.  
현재는 **Greenhouse 기반 공고 수집**을 중심으로,  
외부 공고 수집 → 원천 데이터 저장 → 정규화 → 기술스택 추출 → 사용자 맞춤 추천 흐름을 검증하는 것을 목표로 합니다.

---

## 1. 현재 구현 범위

### 구현 완료
- Greenhouse API 기반 채용공고 조회
- 공고 원본(raw) 데이터 저장
- 공고 정규화(normalized) 처리
- IT 직군 여부 판별
- 기술스택 추출 및 저장
- 사용자 프로필/보유기술 기반 추천 점수 계산
- 추천 결과에 대한 LLM 분석 문구 생성

### 확장 예정
- Greenhouse 외 다른 공고 소스 연동
- 국가별 공고 소스 확장
- 추천 로직 고도화
- 프론트엔드 연결

---

## 2. 패키지 구조

```text
com.carrerlens.demo
├─ global
│  └─ config
│     └─ RestClientConfig
│
├─ job
│  ├─ client
│  │  ├─ greenhouse
│  │  │  ├─ GreenhouseClient
│  │  │  └─ dto
│  │  │     ├─ GreenhouseJobsResponse
│  │  │     ├─ GreenhouseJobItem
│  │  │     ├─ GreenhouseDepartmentDto
│  │  │     ├─ GreenhouseOfficeDto
│  │  │     └─ GreenhouseLocationDto
│  │  └─ level
│  │     └─ LevelClient
│  │
│  ├─ source
│  │  ├─ CollectedJobDto
│  │  ├─ JobSourceFetcher
│  │  ├─ SourceType
│  │  ├─ GreenhouseJobSourceFetcher
│  │  └─ LevelJobSourceFetcher
│  │
│  ├─ controller
│  │  └─ JobTestController
│  │
│  ├─ entity
│  │  ├─ JobPostingRaw
│  │  ├─ JobPostingNormalized
│  │  ├─ JobPostingSkill
│  │  └─ SkillTag
│  │
│  ├─ repository
│  │  ├─ JobPostingRawRepository
│  │  ├─ JobPostingNormalizedRepository
│  │  ├─ JobPostingSkillRepository
│  │  └─ SkillTagRepository
│  │
│  ├─ service
│  │  ├─ JobSyncService
│  │  ├─ JobNormalizationService
│  │  └─ JobSkillExtractionService
│  │
│  └─ dto
│     └─ JobSkillResponse
│
├─ user
│  ├─ controller
│  ├─ dto
│  ├─ entity
│  ├─ repository
│  └─ service
│
└─ recommendation
   ├─ controller
   ├─ dto
   └─ service
   ```

   

---

## 3. 계층별 역할

### job/client
외부 채용공고 API 호출 전담 계층입니다.
현재는 Greenhouse API 호출을 담당합니다.

### job/source

외부 API 응답을 내부 표준 수집 DTO(CollectedJobDto)로 변환하는 계층입니다.
외부 소스가 달라도 내부 저장 로직은 동일하게 처리할 수 있도록 분리했습니다.

### job/service

공고 동기화, 정규화, 기술스택 추출 등 핵심 비즈니스 로직을 담당합니다.

### job/entity

DB 테이블과 매핑되는 엔티티입니다.

### recommendation

사용자 프로필과 공고 데이터를 기반으로 추천 점수를 계산하고, 추천 결과를 생성합니다.

### user

사용자 프로필, 희망 국가/직무, 보유 기술 정보를 관리합니다.

---
## 4. 전체 처리 흐름
### 1) 외부 공고 조회

GreenhouseClient가 외부 Greenhouse API를 호출합니다.

### 2) 내부 수집 DTO 변환

GreenhouseJobSourceFetcher가 Greenhouse 응답을 CollectedJobDto 형태로 변환합니다.

### 3) Raw 저장

JobSyncService가 CollectedJobDto를 JobPostingRaw 엔티티로 저장합니다.

### 4) 정규화

JobNormalizationService가 raw 데이터를 기반으로:

1. IT 직군 여부 판별
2. 직무 카테고리 추출
3. 직무명 정규화
4. 국가/도시 추출

을 수행하여 JobPostingNormalized에 저장합니다.

### 5) 기술스택 추출

JobSkillExtractionService가 공고 본문을 분석하여 기술스택을 추출하고
JobPostingSkill, SkillTag와 연결합니다.

### 6) 추천 생성

RecommendationService가 사용자 프로필, 보유 기술, 희망 국가/직무를 기준으로
공고별 점수를 계산하고 추천 결과를 생성합니다.

### 7) LLM 분석

상위 추천 공고에 대해 LlmAnalysisService가 설명 문구를 생성합니다.

--- 
## 5. 현재 테스트 흐름

#### 현재는 프론트엔드 없이 Postman 기반으로 백엔드 파이프라인을 검증합니다.

### 권장 테스트 순서
1. 공고 수집
2. 정규화
3. 기술스택 추출
4. 사용자 생성/기술 등록
5. 추천 요청

---
## 6. 주요 테스트 API
### 공고 조회

GET /test/jobs/board/{boardToken}

예시: 특정 Greenhouse 보드 공고 조회
raw 저장

POST /test/jobs/sync/{boardToken}

Greenhouse 공고를 job_posting_raw 테이블에 저장

### 정규화

POST /test/jobs/normalize

raw 데이터를 normalized 테이블에 반영
기술스택 추출

POST /test/jobs/extract-skills

IT 공고 대상 기술스택 추출 및 저장
공고 기술스택 조회

GET /test/jobs/{jobId}/skills

--- 
## 7. 추천 흐름 요약

추천은 다음 정보를 기준으로 계산됩니다.

1. 사용자 보유 기술
2. 사용자 희망 국가
3. 사용자 희망 직무
4. 공고 요구 기술스택
5. 공고 국가/직무 정보

현재 추천 점수는 룰 기반으로 계산하며,
상위 추천 공고에 대해 LLM 분석 결과를 추가합니다.

---
## 8. 참고 사항
현재 외부 공고 소스는 Greenhouse 중심으로 구현되어 있습니다.
LevelClient, LevelJobSourceFetcher는 확장 실험용/추후 연동 후보입니다.
현재 정규화와 기술스택 추출은 초기 프로토타입 기준의 룰 기반 방식입니다.
현재 구조는 다중 공고 소스 확장을 고려하여 client와 source 계층을 분리한 상태입니다.

---
## 9. 향후 개선 방향
1. Greenhouse 외 추가 공고 소스 연동
2. 일본 공고 소스 확장
3. 정규화 로직 정밀도 향상
4. 기술스택 추출 정확도 향상
5. 추천 점수 로직 고도화
6. 프론트엔드 연결

---


## 10. 한 줄 요약

- `client`: 외부 API 호출
- `source`: 외부 응답을 내부 수집 형태로 변환
- `service`: 저장/정규화/추천 처리
- `entity/repository`: DB 저장소

