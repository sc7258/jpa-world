# World DB API

Spring Boot 기반의 World Database API 프로젝트입니다. MySQL의 World 샘플 데이터베이스를 활용하여 도시(City), 국가(Country), 국가별 언어(CountryLanguage) 정보를 REST API로 제공합니다.

## 기술 스택

- **Backend**: Spring Boot 3.x, Kotlin
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA
- **SQL Builder**: jOOQ
- **API Documentation**: Springdoc OpenAPI (Swagger)
- **Containerization**: Docker, Docker Compose

## 프로젝트 구조

```
jpa-world/
├── src/
│   ├── main/
│   │   ├── kotlin/com/sc7258/jpaworld/
│   │   │   ├── controller/       # REST API 컨트롤러
│   │   │   ├── entity/           # JPA 엔티티 클래스
│   │   │   ├── repository/       # Spring Data JPA 리포지토리
│   │   │   ├── service/          # 비즈니스 로직 및 jOOQ 쿼리 서비스
│   │   │   └── JpaWorldApplication.kt
│   │   └── resources/
│   │       └── application.yml   # 애플리케이션 설정
│   └── test/                     # 테스트 코드
├── docker-compose.yml            # Docker Compose 설정
├── init.sql                      # MySQL 초기화 스크립트 (World DB)
└── build.gradle.kts              # Gradle 빌드 설정
```

## 설치 및 실행 방법

### 사전 요구사항

- Docker 및 Docker Compose
- JDK 17 이상
- Gradle 7.x 이상

### 데이터베이스 설정

1. 프로젝트 루트 디렉토리에서 Docker Compose를 사용하여 MySQL 컨테이너를 실행합니다:

```bash
docker-compose up -d
```

이 명령은 MySQL 8.0 컨테이너를 시작하고, `init.sql` 스크립트를 실행하여 World 데이터베이스를 초기화합니다.

### 애플리케이션 실행

1. Gradle을 사용하여 애플리케이션을 빌드하고 실행합니다:

```bash
./gradlew bootRun
```

또는 IDE(IntelliJ IDEA 등)에서 `JpaWorldApplication.kt` 파일을 실행합니다.

2. 애플리케이션은 기본적으로 `http://localhost:8080/api/v1`에서 실행됩니다.

## API 문서

Swagger UI를 통해 API 문서를 확인하고 테스트할 수 있습니다:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## API 엔드포인트

### 기본 JPA 엔드포인트

- **도시(City) API**: `/api/v1/cities`
  - 모든 도시 조회: `GET /api/v1/cities`
  - ID로 도시 조회: `GET /api/v1/cities/{id}`
  - 이름으로 도시 검색: `GET /api/v1/cities/search/name?name={name}`
  - 국가 코드로 도시 검색: `GET /api/v1/cities/search/country?countryCode={code}`
  - 지역으로 도시 검색: `GET /api/v1/cities/search/district?district={district}`
  - 인구수로 도시 검색: `GET /api/v1/cities/search/population?minPopulation={population}`

### jOOQ 고급 검색 엔드포인트

- **도시 고급 검색 API**: `/api/v1/query/cities`
  - 인구수로 도시 검색: `GET /api/v1/query/cities/search/population?minPopulation={population}`
  - 이름과 국가로 도시 검색: `GET /api/v1/query/cities/search/name-country?name={name}&countryCode={code}`

- **국가 고급 검색 API**: `/api/v1/query/countries`
  - 인구수와 대륙으로 국가 검색: `GET /api/v1/query/countries/search/population-continent?minPopulation={population}&continent={continent}`
  - 기대 수명으로 국가 검색: `GET /api/v1/query/countries/search/life-expectancy?minLifeExpectancy={expectancy}`
  - GNP 범위로 국가 검색: `GET /api/v1/query/countries/search/gnp-range?minGnp={min}&maxGnp={max}`

## 엔티티 관계

- **City**: 도시 정보 (ID, 이름, 국가, 지역, 인구수)
- **Country**: 국가 정보 (코드, 이름, 대륙, 지역, 면적, 독립연도, 인구수 등)
- **CountryLanguage**: 국가별 언어 정보 (국가, 언어, 공식 여부, 사용 비율)

## 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.