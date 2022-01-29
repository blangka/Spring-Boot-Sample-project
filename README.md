# Spring-Boot-Sample-project


### 프로젝트 목적
    프로젝트 초기 생성시 기본이 되는 세팅을 정리 하기 위함

### Spec
	1. Spring boot 2.5.7 + war + java 11
    2. JPA + QueryDSL + H2

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
        
### Profile
    1. 참고 : https://jjunii486.tistory.com/227
    2. resources-{profile} 로 yml 파일 분리
    3. add configuration 에 active profile 에 local 로 하면 해당 경로의 yml 불러 온다

### TDD
    1. 검증을 위해 application-test.yml 만듬
    2. cnt + alt + t 를 눌러서 테스트 케이스 생성해서 테스트 함