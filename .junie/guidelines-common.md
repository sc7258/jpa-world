# Junie Common Coding Guidelines for [Project Name]

## Purpose
이 가이드라인은 JetBrains Junie가 프로젝트에서 일관된 코딩 표준을 유지하고, 작업 완료 후 불필요한 코드를 제거하여 코드 품질을 최적화하기 위한 공통 규칙을 정의합니다. 모든 작업은 이 가이드라인을 기본으로 따르며, 작업별 가이드라인에서 추가 지침을 제공할 수 있습니다.

## General Guidelines
- **언어 및 버전**:
    - 기본: Java 17, Spring Boot 3.x.
    - 선택적: Kotlin, Python 3.11, TypeScript 5.x (프로젝트 요구사항에 따라 조정).
- **코딩 스타일**:
    - [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 준수 (Java/Kotlin).
    - Python: [PEP 8](https://www.python.org/dev/peps/pep-0008/).
    - TypeScript: Airbnb JavaScript Style Guide.
    - 들여쓰기: Java/Kotlin은 2칸, Python은 4칸.
    - 최대 줄 길이: 100자.
    - 명명 규칙: 변수, 메서드, 클래스 이름은 명확하고 의미 있게(예: `calculateTotalPrice` 대신 `calc` 금지).
- **파일 구조**:
    - 표준 프로젝트 구조 준수:
        - Java/Spring Boot: `src/main/java/com/example/project`.
        - Python: `src/[module]/`.
        - TypeScript: `src/components/`, `src/services/`.
    - 불필요한 디렉토리나 파일 생성 금지.
- **의존성 관리**:
    - Java: Maven 또는 Gradle 사용.
    - Python: `requirements.txt` 또는 `poetry`.
    - TypeScript: `package.json` 및 `npm`/`yarn`.
    - 사용하지 않는 의존성은 추가하지 않으며, 추가 시 주석으로 이유 명시.

## Code Generation and Cleanup
- **코드 생성**:
    - 프로젝트 컨텍스트에 맞는 파일(예: 엔티티, 서비스, 컨트롤러)만 생성.
    - 임시 파일, 디버깅용 코드, 또는 불완전한 코드는 생성 금지.
- **불필요한 코드 제거**:
    - **작업 완료 후** 다음을 자동 제거:
        - 사용되지 않는 import 문(예: Java의 `import java.util.List` 미사용 시).
        - 호출되지 않는 메서드, 클래스, 변수.
        - 디버깅용 로그(예: `System.out.println`, `console.log`, `print()`).
        - 주석으로 표시된 TODO, 임시 코드, 또는 기능에 영향을 주지 않는 주석.
    - 제거 전, 코드가 프로젝트 기능에 영향을 미치지 않는지 확인.
    - **제외 항목**: 테스트 코드, 설정 파일(예: `application.properties`, `pyproject.toml`), 문서화된 주석(예: Javadoc, Python docstring).
- **리팩토링**:
    - 중복 코드 제거(예: 동일 로직 반복 시 메서드로 추출).
    - 긴 메서드(20줄 초과) 또는 복잡한 조건문 단순화.
    - 가독성 향상을 위해 메서드 분리 및 명명 개선.

## Testing
- **테스트 작성**:
    - 모든 새로운 기능에 단위 테스트와 통합 테스트 작성.
    - 프레임워크:
        - Java: JUnit 5, Mockito.
        - Python: pytest, unittest.
        - TypeScript: Jest, Vitest.
- **테스트 실행**:
    - 코드 변경 및 정리 후 모든 테스트 실행.
    - 테스트 실패 시, 오류 원인 분석 및 수정 제안.
- **커버리지 목표**:
    - 최소 80% 코드 커버리지.
    - 부족 시 추가 테스트 작성.
- **오류 처리**:
    - Java: `@ExceptionHandler`, `ResponseEntity`로 예외 처리.
    - Python: `try-except` 블록 사용, 사용자 친화적 오류 메시지.
    - TypeScript: `try-catch`, 커스텀 Error 클래스 활용.

## Post-Task Actions
- **작업 완료 후**:
    1. IDE 코드 검사(Inspections) 실행하여 경고 및 오류 최소화.
    2. 불필요한 파일(임시 파일, 중복 파일) 삭제.
    3. 프로젝트 빌드 및 모든 테스트 실행.
    4. 변경 사항 요약 제공:
        - 생성/수정된 파일 목록.
        - 제거된 코드 및 이유(예: "미사용 import 5개 제거").
        - 테스트 결과 및 커버리지 보고서.
- **Brave Mode**:
    - 활성화 시, 코드 정리 및 리팩토링 자동 수행.
    - 변경 사항은 커밋 전 사용자에게 검토 요청.
- **보고서 형식**:
  ```markdown
  # 작업 요약
  - **작업**: [작업 설명]
  - **생성/수정된 파일**: [파일 목록]
  - **제거된 코드**: [제거 항목 및 이유]
  - **테스트 결과**: [성공/실패 여부, 커버리지]
  ```

## Version Control
- **Git 사용**:
    - 모든 변경 사항은 Git에 커밋.
    - 커밋 메시지: 작업 내용 요약(예: "Add Book REST API with CRUD operations").
    - 불필요한 코드 제거 후, Git diff로 변경 사항 검토.
- **제외 항목**:
    - 민감한 정보(예: API 키, 비밀번호) 포함 파일은 커밋 금지.
    - `.gitignore`에 따라 임시 파일 제외.

## References
- JetBrains Junie Guidelines: https://github.com/JetBrains/junie-guidelines
- Google Java Style Guide: https://google.github.io/styleguide/javaguide.html
- Python PEP 8: https://www.python.org/dev/peps/pep-0008/
- Airbnb JavaScript Style Guide: https://github.com/airbnb/javascript
- Spring Boot Documentation: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/