# Spring-Boot-Sample-project


### 프로젝트 목적
    프로젝트 초기 생성시 기본이 되는 세팅을 정리 하기 위함.
    추가로 처음 보는 사람도 이해 할수 있도록 설명을 최대한 많이 달아 놓음.

### 프로젝트 간략 기능
    1. 회원 기능, 상품 기능 , 주문 기능이 있다.
    2. 회원을 관리하고 주문하며 상품은 재고 관리 하도록 한다.

### 실행 방법
    $ ./gradlew bootRun --args='--spring.profiles.active=local'

### Spec
	1. Spring boot 2.5.7 + war + java 11.
    2. JPA + QueryDSL + H2.

### DB구조
![db](https://user-images.githubusercontent.com/98309975/151704961-f7e7bfea-e21c-4351-ad15-d6ea4b860d93.png)

### Path Tree
    1. entity : JPA Domain
    2. repo : JPA Repository
    3. api : api 용도
    4. controller 
    5. service 
    6. queryService : 쿼리 조회
    7. config : 설정 관련 configuration
    8. util

### 라이브러리
    1. spring-boot-starter-web 
        spring-boot-starter-tomcat: 톰캣 (웹서버)
        spring-webmvc: 스프링 웹 MVC
    2. spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View) spring-boot-starter-data-jpa
    3. JDBC 
        HikariCP 커넥션 풀 (부트 2.0 기본)
        hibernate + JPA: 하이버네이트 + JPA
    4. logging
        spring-boot-starter-logging logback, slf4j
    5. Test
        junit: 테스트 프레임워크
        mockito: 목 라이브러리
        assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리 
        spring-test: 스프링 통합 테스트 지원

### Spring Security + jwt
    https://github.com/SimKyunam/item-bank

### Thymeleaf
    1. 참고 site : https://www.thymeleaf.org/
    2. 위치 : resources/templates/ hello.html
    3. 호출 방법 : Controller 에서 return 에 정의한 String으로 호출함
    4. spring-boot-devtools
        세팅해서 변경시 재부팅 필요 없이 리로딩하도록함
        tomcat 올라올떄 <INFO 3701 --- [  restartedMain] com.hkmc.sample.SampleApplication> 찍히면 적용된거임
    5. Build -> reproject 혹은 recomfile하면 화면 변경

### H2 DB
    1. 다운로드
        site : https://www.h2database.com
    2. 실행
        받은 위치에서 h2/bin/h2.sh 실행하면 됨 
        브라우져가 뜨고 나면  ex ) http://218.38.137.28:8082/?key=b0a40b46b84cbea67ba1584ad020437a2afdc4eeeb6496f0637b894e9c9a39bf
        에서 압부분만 localhost로 바꾸어 주어야 한.
    3. 데이터베이스 파일 생성 방법
        jdbc:h2:~/sample (최소 한번)
        ~/sample.mv.db 파일 생성 확인
        이후 부터는 jdbc:h2:tcp://localhost/~/sample 이렇게 접속
        
### Swagger 2
    API 검즐을 위한 swagger 적용(http://localhost:8080/swagger-ui/)

### log 방법
    1. AOP 를 활용하는 방법 : https://hirlawldo.tistory.com/42?category=874134
    2. Interceptor 활용방법 : https://hirlawldo.tistory.com/44?category=874134

### Object 간의 mapping 을 위해 사용
    ModelMapper (https://www.baeldung.com/java-modelmapper)

### Custom Respose,CustomError
    동일한 Response Body 를 가지도록 세팅
    1. Response가 성공인 경우
        {
            "resultCode": "200",
            "resultMessage": "SUCCESS",
            "result": 1
        }
    2. Response가 Error인 경우
        common/error/ 하위에 exception custom 추가
        {
            "resultCode": "S0001",
            "resultMessage": "기 가입된 회원입니다."
        }

### Profile
    1. resources/properties-{profile}에 properties 파일 분리
    2. @PropertySource(value = "classpath:properties-${spring.profiles.active}/application.properties", ignoreResourceNotFound = true) 로 설정

### JPA 
    1. 가급적이면 단방향 연관 관계를 쓰는것이 좋다.  ex)주문에 회원을 가지고있고  회원은 주문을 가지고 있지 않아야 한다.
    2. 다대다 연결관계는 왠만하면 만들지 말도록
    3. 연관관계 주민은 항상 외래키가 있는 곳을 연관 관계 주인으로 하여라 일대다 관계이면 다가 무조건 주인이다.
    4. 모든 연관관계는 지연로딩으로 설정 @XToOne은 기본이 즉시 로딩이여서 지연 로딩으로 바꿔주어야 한다
    5. OneToOne은 둘다 JsonIgnore설정, OneToMany는 @JsonIgnore설정
    6. spring.jpa.open-in-view=false DB 커넥션을 물고 있기 떄문에 service,repository
        에서만 물고 있도록 해야 한다. 지연 로딩이 안된다

### TDD
    1. 검증을 위해 test/java/application.yml 만듬
    2. cnt + alt + t 를 눌러서 테스트 케이스 생성해서 테스트 함

### 추가 참고사항
    1. setter 의 사용은 피한다. 비즈니스 로직으로 명시적으로 설정한다