
# 🛠 Project Guidelines: World DB API with Spring Boot, JPA, Swagger, jOOQ

---

## 📦 1. MySQL Sample DB 설정 with Docker Compose

`docker-compose.yml` 파일을 프로젝트 루트에 생성하세요:

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: world-db
    restart: always
    environment:
      MYSQL_DATABASE: world
      MYSQL_ROOT_PASSWORD: root
    ports:
      - "3306:3306"
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
```

> 💡 **init.sql** 파일에 [MySQL world DB sample SQL](https://dev.mysql.com/doc/index-other.html)을 복사하여 저장하세요.

---

## ☕ 2. Spring Boot + Spring Data JPA + Swagger 구성

### ✅ 2.1. 프로젝트 생성

- IntelliJ → New Project → Spring Boot (Gradle 또는 Maven 선택)
- Dependencies 추가:
    - Spring Web
    - Spring Data JPA
    - MySQL Driver
    - Spring Boot DevTools
    - Springdoc OpenAPI (Swagger)
    - (Optional) jOOQ

### ✅ 2.2. `application.yml` 설정

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/world
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
    properties:
      hibernate.format_sql: true

  mvc:
    servlet:
      path: /api/v1

springdoc:
  swagger-ui:
    path: /swagger-ui.html
  api-docs:
    path: /api-docs
  pathsToMatch: /**
```

### ✅ 2.3. Entity 및 Repository 생성 예시

#### 2.3.1. Entity 클래스 (Kotlin)

```kotlin
@Entity
@Table(name = "city")
data class City(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0,

    @Column(name = "name", length = 35, nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code", referencedColumnName = "code", nullable = false)
    @JsonIgnoreProperties("cities", "languages")
    val country: Country,

    @Column(name = "district", length = 20, nullable = false)
    val district: String,

    @Column(name = "population", nullable = false)
    val population: Int
)

@Entity
@Table(name = "country")
data class Country(
    @Id
    @Column(name = "code", length = 3)
    val code: String,

    @Column(name = "name", length = 52, nullable = false)
    val name: String,

    // 기타 필드 생략...
) {
    @JsonIgnore
    @OneToMany(mappedBy = "country")
    lateinit var cities: List<City>

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    lateinit var languages: List<CountryLanguage>
}

@Entity
@Table(name = "countrylanguage")
@IdClass(CountryLanguageId::class)
data class CountryLanguage(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code", referencedColumnName = "code", nullable = false)
    @JsonIgnoreProperties("cities", "languages")
    val country: Country,

    @Id
    @Column(name = "language", length = 30, nullable = false)
    val language: String,

    @Column(name = "is_official", nullable = false)
    val isOfficial: String,

    @Column(name = "percentage", nullable = false)
    val percentage: Float
)

data class CountryLanguageId(
    val country: String = "",
    val language: String = ""
) : Serializable
```

#### 2.3.2. Repository 인터페이스 (Kotlin)

```kotlin
@Repository
interface CityRepository : JpaRepository<City, Long> {
    fun findByName(name: String): List<City>
    fun findByCountry(country: Country): List<City>
    fun findByDistrict(district: String): List<City>
    fun findByPopulationGreaterThan(population: Int): List<City>
}

@Repository
interface CountryRepository : JpaRepository<Country, String> {
    fun findByName(name: String): List<Country>
    fun findByContinent(continent: String): List<Country>
    fun findByPopulationGreaterThan(population: Int): List<Country>
}

@Repository
interface CountryLanguageRepository : JpaRepository<CountryLanguage, CountryLanguageId> {
    fun findByCountry(country: Country): List<CountryLanguage>
    fun findByLanguage(language: String): List<CountryLanguage>
    fun findByIsOfficial(isOfficial: String): List<CountryLanguage>
    fun findByPercentageGreaterThan(percentage: Float): List<CountryLanguage>
}
```

### ✅ 2.4. REST Controller 예시 (Kotlin)

```kotlin
@RestController
@RequestMapping("/cities")
@Tag(name = "City", description = "City API")
class CityController(
    private val cityRepository: CityRepository,
    private val countryRepository: CountryRepository
) {

    @GetMapping
    @Operation(summary = "Get all cities", description = "Retrieves a list of all cities")
    fun findAll(): List<City> = cityRepository.findAll()

    @GetMapping("/{id}")
    @Operation(summary = "Get city by ID", description = "Retrieves a city by its ID")
    fun findById(@PathVariable id: Long): ResponseEntity<City> =
        cityRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping("/search/name")
    @Operation(summary = "Search cities by name", description = "Retrieves cities by name")
    fun findByName(@RequestParam name: String): List<City> =
        cityRepository.findByName(name)

    // 기타 검색 메서드 생략...
}
```

> 🔍 Swagger UI는 `/swagger-ui.html` 또는 `/api-docs`에서 확인 가능

---

## 🔍 3. jOOQ로 고급 검색 기능 추가 (선택사항)

### ✅ 3.1. jOOQ 설정

Gradle 예시:

```kotlin
implementation("org.jooq:jooq")
```

### ✅ 3.2. jOOQ DSLContext를 통한 검색 (Kotlin)

```kotlin
@Service
class CityQueryService(private val dsl: DSLContext) {

    fun searchByPopulation(minPop: Int): List<CityDto> {
        return dsl.selectFrom("city")
            .where("population > ?", minPop)
            .fetchInto(CityDto::class.java)
    }

    fun searchByNameAndCountry(name: String, countryCode: String): List<CityDto> {
        return dsl.selectFrom("city")
            .where("name like ? and country_code = ?", "%$name%", countryCode)
            .fetchInto(CityDto::class.java)
    }
}

data class CityDto(
    val id: Long,
    val name: String,
    val countryCode: String,
    val district: String,
    val population: Int
)
```

### ✅ 3.3. 고급 검색을 위한 Query Controller (Kotlin)

```kotlin
@RestController
@RequestMapping("/query/cities")
@Tag(name = "City Query", description = "Advanced City Query API using jOOQ")
class CityQueryController(private val cityQueryService: CityQueryService) {

    @GetMapping("/search/population")
    @Operation(summary = "Search cities by minimum population", description = "Advanced search for cities with population greater than the specified value using jOOQ")
    fun searchByPopulation(@RequestParam minPopulation: Int): List<CityDto> =
        cityQueryService.searchByPopulation(minPopulation)

    @GetMapping("/search/name-country")
    @Operation(summary = "Search cities by name and country", description = "Advanced search for cities by name pattern and country code using jOOQ")
    fun searchByNameAndCountry(
        @RequestParam name: String,
        @RequestParam countryCode: String
    ): List<CityDto> =
        cityQueryService.searchByNameAndCountry(name, countryCode)
}
```

---

## ✅ 실행 순서 요약

1. `docker-compose up -d` 로 world DB 실행
2. IntelliJ에서 프로젝트 실행
3. Swagger UI에서 API 테스트 (`/swagger-ui.html`)
4. 기본 API 경로는 `/api/v1`로 설정됨 (예: `/api/v1/cities`)
5. jOOQ 검색 API는 `/api/v1/query` 경로로 접근 가능 (예: `/api/v1/query/cities/search/population`)
