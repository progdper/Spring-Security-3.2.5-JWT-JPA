# Spring Security(3.2.5) + JWT + JPA 를 이용하여 REST API 작성하기

## 요약
- NodeJs 프레임워크인 Express 기준으로 model, controller, routes 폴더 구조를 유지하도록
Package를 구성함.
- Get 이외의 API 요청이 있을 때마다 JWT 토큰을 이용하여 사용자 정보를 추출 및 비교하여 본인
인증을 하도록 함.
- 간단한 spring 관련 영상과 도서 및 github를 참고하여 Express에서 만들었던 REST API와 비슷한 순서로
작성하였음.
- 사용자 등록시 token 발행하여 게시글 생성, 수정, 삭제 / 본인의 정보를 수정, 삭제 / token 갱신 함.
- refreshToken은 DB에 등록하여 재발행시 비교하여 시간이 남더라도 사용할 수 없도록 함.
- DB를 h2로 하였음에 바로 테스트 가능하며 mariaDB를 추가하였음.

## 의문사항
- 다른 사용자가 작성한 security 에서 자주 보이는 role은 왜 필요한가?
- SecurityContextHolder 에 사용자 정보를 등록 할 경우 무상태성(Stateless)이 유지 되는가?
- 객체 지향(캡술화, 상속화, 다형성, 추상화)이 제대로 되었는가?
- 도대체 객체 지향은 무엇인가?????

## 폴더구조
```
demo
 ┣ config   // 설정
 ┃ ┣ jwt
 ┃ ┗ security
 ┣ controller_RouteAndController    // route와 controller
 ┣ domain_DB    // DB 구성
 ┣ dto_DBdata   // service 및 controller에서 DB를 사용할수 있도록 처리
 ┃ ┣ request
 ┃ ┣ response
 ┣ payload  // jwt 반환
 ┣ repository_ORM_jpa   // ORM으로 DB 조작
 ┣ returnCode   // controller 오류 또는 성공 관련 확인
 ┣ service_Model    // DB 조작을 수행
 ┗ DemoApplication.java     // 실행파일
```

## 참고
> [Spring Security Refresh Token with JWT](https://www.bezkoder.com/spring-security-refresh-token/) : cookie로 jwt를 전달하는 방법으로 작성

> [JWT Auth with Spring Boot (No Refresh Token)](https://github.com/DevRezaur/spring-security-JWT-module) : 전반적인 code 내용 확인 및 참고

> [스프링부트3 백엔드 개발자 되기 자바편 2판](https://github.com/shinsunyoung/springboot-developer) : 전반적인 code 내용 확인 및 참고

> [SPRING BOOT in PRACTICE](https://github.com/spring-boot-in-practice/repo) : spring boot의 관련 class 등의 내용이 상세히 있음(작성일 기준 아직 이해 못함)

> [스프링부트 쇼핑몰 프로젝트 with JPA](https://github.com/roadbook2/shop) : 전반적인 code 확인

> [기록잡화점 게시판](https://github.com/sosow0212/ApiStudy) : code별 설명이 자세히 있음(다 작성 후 확인함)

## 기타
작성일 기준 spring 관련 업무는 미정으로 언제 다시 할지 모름.