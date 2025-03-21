# SPRING PLUS

## 레벨 1

### 1. 코드 개선 퀴즈 - @Transactional의 이해
- 클래스 `Transactional` 이 설정이 리드온리로 되어 있어서 세이브 하는 과정에서 오류 발생습니다.
- `saveTodo` 메서드에 `@Transactional` 추가 함으로 서 이 메서드에 한해 기존 `@Transactional(readOnly = true)` 설정을 덮어 씌어주었습니다.

---

### 2. 코드 추가 퀴즈 - JWT의 이해

- `User` 엔티티에 `nickname` 컬럼을 추가했습니다.
- `nickname` 컬럼은 중복 가능하게 설정했습니다.
- 이와 관련해서 기존 로직의 인자 값을 수정했습니다.
- JWT에 `nickname` 값이 포함되도록 수정했습니다.

---

### 3. 코드 개선 퀴즈 -  JPA의 이해

- 할 일 검색 시 `weather` 및 기간 조건으로도 검색할 수 있도록 `@RequestParam` 으로 해당 값을 받도록 했습니다.
- `JpaSpecification` 을 활용하여 동적 쿼리를 적용했습니다.

---

### 4. 테스트 코드 퀴즈 - 컨트롤러 테스트의 이해

- 테스트 패키지 `org.example.expert.domain.todo.controller`의 `todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다()` 테스트에서 실제로 `400`을 반환하고 있지만, 테스트에선 `200`으로 검증하고 있기에 `400` 검정으로 수정했습니다.

---

### 5. 코드 개선 퀴즈 - AOP의 이해
- `UserAdminController` 클래스의 `changeUserRole()` 메소드가 실행 전 동작할 수 있도록, `AdminAccessLoggingAspect` 클래스에 있는 AOP를 기존 `@After`에서 `@Before`로 수정했습니다.
---

### 레벨 3. 테스트 코드 연습 - 2.1
- **기존**
`managerService.getManagers` 메서드가 `InvalidRequestException` 예외를 던지면서 `"Todo not found"` 메세지를 반환
=> 하지만 테스트 코드에선 `"Manager not found"` 메세지로 검증한 상태.

- **변경**
`assertEquals("Todo not found", exception.getMessage());` 으로 변경

---

## Level. 2 

### 6. JPA Cascade

- JPA의 `cascade` 기능을 활용해 할 일을 생성한 유저가 담당자로 등록될 수 있게 수정했습니다.            
          
---

### 7. N+1

- `JOIN fetc`을 이용하여 `CommentController` 클래스의 `getComments()` API를 호출할 때 발생하는 `N+1 문제`를 해결했습니다.

---

### 8. QueryDSL

- JPQL로 작성된 `findByIdWithUser` 를 `QueryDSL`로 변경했습니다


---

### 9. Spring Security

- 기존 `Filter`와 `Argument Resolver`를 사용하던 코드들을 `Spring Security`로 변경했습니다.

---

## Level 3 

### 10. QueryDSL 을 사용하여 검색 기능 만들기

- QueryDSL를 사용하여 일정을 검색할 수 있도록 `searchTodos` API를 구현했습니다.
- 검색 결과는 페이징 되어 반환되게 했습니다.

---

### 11. Transaction 심화

- 매니저 등록 요청을 기록하는 로그 테이블를 만들었습니다.
- 매니저 등록 요청 시에 로그가 남기에, 요청 성공 여부와는 별개로 동작합니다.















