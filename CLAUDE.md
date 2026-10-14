# CLAUDE.md

## 프로젝트 개요

Spring PetClinic 변형 프로젝트(`petclinic-jpa`)로, JPA/H2 데이터 모델 위에 만든 **읽기 전용 REST API**입니다. `GET` 엔드포인트만 있으며 생성/수정/삭제 기능은 없습니다.

- Java 17(Gradle toolchain), Spring Boot 4.1.1, Gradle Kotlin DSL
- Spring Data JPA, spring-boot-starter-webmvc, Thymeleaf(선언만 되어 있고 아직 미사용), springdoc-openapi 3.1.1(Swagger UI)
- H2 인메모리 DB. H2 콘솔과 devtools는 `developmentOnly`
- 기본 패키지: `com.example.petclinic`

## 명령어

```bash
./gradlew bootRun        # 앱 실행 (Windows: gradlew.bat bootRun)
./gradlew build          # 컴파일 + 테스트 + 패키징
./gradlew test           # 테스트 실행 (JUnit Platform)
./gradlew test --tests com.example.petclinic.PetclinicApplicationTests
```

테스트: `PetclinicApplicationTests`(컨텍스트 로딩)와 `OwnerControllerTests`, `PetControllerTests`(MockMvc로 소유자/동물 API 검증).

## 아키텍처

계층 구조: `controller` -> `service` -> `repository` -> `entity`

- `controller/` – `/api/v1/...` 아래의 `@RestController`: `owners`, `pets`, `vets`, `visits`, `types`(펫 종류), `specialities`, `members`. 각각 `GET` 목록과 `GET /{id}`를 제공합니다(members는 `/{username}`).
- `controller/response/` – 기본 생성자와 전체 필드 생성자, getter/setter를 가진 단순 DTO 클래스(`XxxDto`). 컨트롤러에서 엔티티를 DTO로 직접 변환(`new XxxDto(e.getA(), ...)`)하며, 엔티티를 그대로 반환하지 않습니다.
- `service/` – `@Service @Transactional(readOnly = true)`, 생성자 주입. `findXxxs()` / `findXxx(id)`를 제공하고, private `verifyXxxExists`가 `NoSuchElementException("Xxx does not exist")`를 던집니다.
- `repository/` – 엔티티별 Spring Data JPA 리포지토리.
- `entity/` – getter/setter를 직접 작성한 JPA 엔티티(Lombok 미사용).
- `LoadDatabase` – 앱 시작 때마다 샘플 데이터를 모두 넣는 `CommandLineRunner`를 가진 `@Configuration`(수의사, 전문 분야, 펫 종류, 소유자, 펫, 방문 기록, 역할 3개를 가진 `admin` 회원 1명).

### 구현된 과제

- **과제 1 – 소유자 목록/검색**
  - `GET /api/v1/owners` : 모든 소유자 목록
  - `GET /api/v1/owners?lastname={값}` : 입력받은 `lastname` 값으로 시작하는 성(lastName)을 가진 소유자 목록. `kim`은 예시일 뿐 임의의 값을 받을 수 있음 (대소문자 무시, 결과 없으면 빈 배열, 빈 값이면 전체 목록)
  - 구현: `OwnerRepository.findByLastNameStartingWithIgnoreCase` -> `OwnerService.findOwnersByLastName` -> `OwnerController.list(@RequestParam lastname)`

- **과제 2 – 생후 n년 미만 동물 조회 (예방접종 대상 선정)**
  - `GET /api/v1/pets` : 모든 동물 목록
  - `GET /api/v1/pets?year={n}&type={name}` : 현재 날짜 기준 생후 n년 미만(`birthDate > now - n년`)이고 종류 이름이 `type`인 동물 (종류는 대소문자 무시)
  - `year`와 `type` 중 하나만 전달하면 서비스에서 `IllegalArgumentException`을 던지고, `GlobalExceptionHandler`가 HTTP 400으로 변환
  - 구현: `PetRepository.findByTypeNameIgnoreCaseAndBirthDateAfter` -> `PetService.findPetsYoungerThan` -> `PetController.list(year, type)`

### 데이터 모델 참고

- `Person`은 **SINGLE_TABLE** 상속 계층의 부모입니다(테이블 `people`, 구분 컬럼 `dtype`, ID 컬럼 `person_id`). `Owner`(`OWNER`)와 `Vet`(`VET`)이 상속합니다.
- Vet와 Speciality의 관계는 `@ManyToMany`가 아니라 명시적 조인 엔티티 `VetSpeciality`로 표현합니다.
- `Member`(`members`, PK는 `username`)와 `Role`은 Spring Security 스타일의 인증 테이블입니다. 비밀번호는 `{noop}admin`으로 저장되는 샘플 데이터이며, Spring Security는 아직 의존성에 없습니다.
- 모든 연관관계는 `@ManyToOne(fetch = LAZY)`이고 `@OneToMany` 역참조는 없습니다. 지연 로딩 문제를 피하려면 트랜잭션 범위 안에서 DTO로 변환해야 합니다.
- `Pet.birthDate`와 `Visit.date`는 `LocalDateTime`입니다.

## 설정

`src/main/resources/application.yml`은 현재 **비어 있어서** 모든 것이 Spring Boot 기본값으로 동작합니다(H2 인메모리, 포트 8080). 포트, H2 콘솔, JPA 로깅 등을 바꾸려면 이 파일에 추가하세요.

springdoc 의존성이 있으므로 실행 중에 Swagger UI(기본 경로 `/swagger-ui.html`)를 쓸 수 있습니다.

## 컨벤션

- 엔티티를 추가할 때는 기존 패턴(엔티티 -> 리포지토리 -> 서비스 -> DTO -> 컨트롤러)을 따르고, 필요하면 `LoadDatabase`에 시드 데이터를 추가합니다.
- 생성자 주입을 사용하고 필드 `@Autowired`는 쓰지 않습니다.
- 서비스는 읽기 전용 트랜잭션입니다. 쓰기 메서드를 추가하면 해당 메서드에 `@Transactional`을 따로 붙이세요.
- 존재하지 않는 리소스는 `GlobalExceptionHandler`(`@RestControllerAdvice`)가 `NoSuchElementException`을 HTTP 404, `IllegalArgumentException`을 HTTP 400(`{status, error, message}`)으로 변환합니다.
- 주의: `Person` 단일 테이블을 Vet와 Owner가 공유하고 ID가 IDENTITY로 발급되므로, 시드 순서상 Vet는 1~6, Owner는 7~16번입니다. `/api/v1/owners/1`처럼 다른 타입의 ID를 조회하면 404가 됩니다.

## Git

원격 저장소: `https://github.com/sds-code-reviewer-26-8/petclinic-40239.git`, 브랜치 `main`. `.idea/`, `.gradle/`, `build/`는 git에서 제외됩니다.
