<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ul>
    <li>
      <a href="#칼빈대학교-평생교육원-리팩토링">About The Project</a>
      <ul>
        <li>
          <a href="#built-with">Built With</a>
          <ul>
            <li><a href="#language">Language</a></li>
            <li><a href="#frameworks">Frameworks</a></li>
            <li><a href="#template-engine">Template Engine</a></li>
            <li><a href="#database">Database</a></li>
            <li><a href="#orm">ORM</a></li>
            <li><a href="#querybuilder">QueryBuilder</a></li>
            <li><a href="#build-tool">Build Tool</a></li>
            <li><a href="#형상-관리-전략">형상 관리 전략</a></li>
          </ul>
        </li>
      </ul>
    </li>
    <li>
      <a href="#diagram">Diagram</a>
      <ul>
        <li><a href="#architecture">Architecture</a></li>
        <li><a href="#erd">ERD</a></li>
      </ul>
    </li>
    <li><a href="#features">Features</a></li>
    <li><a href="#roadmap">RoadMap</a></li>
    <li><a href="#author">Author</a></li>
  </ul>
</details>

# 칼빈대학교 평생교육원 리팩토링
![칼빈대_평생교육원-로고](https://github.com/user-attachments/assets/a78cad9f-3977-46ff-a751-e595f5435c6f)<br>
기존에 진행했던 칼빈대학교 평색교육원 홈페이지 프로젝트에 JPA, SpringData JPA, Querydsl 등 새로운 기술을 적용하고, 기존에 존재하던 문제점을 개선했습니다.<br>
주요 목표 : 
* DB 설계 문제 개선
* DB 부하 개선
* SQL 및 코드 가독성 개선
* 테스트 코드 작성
* 페이징 쿼리 간소화

<p align="right">(<a href="#readme-top">back to top</a>)</p>


## Built With

### Language

* <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white">
* <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white">
* <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white">
* <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white">

### Frameworks

* <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"> v.3.3.5

### Template Engine

* <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white"> v.3.3.5

### Database

* <img src="https://img.shields.io/badge/H2-003D8F?style=for-the-badge&logo=H2&logoColor=white"> v.2.2.224
* <img src="https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=MariaDB&logoColor=white"> v.10.1.13

### ORM

* <img src="https://img.shields.io/badge/JPA(Hibernate)-59666C?style=for-the-badge&logo=hibernate&logoColor=white"> v.6.5.3
* <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=Spring Data JPA&logoColor=white"> v.3.3.5

### QueryBuilder

* <img src="https://img.shields.io/badge/QueryDSL-2596BE?style=for-the-badge&logo=QueryDSL&logoColor=white"> v.5.1.0

### Build Tool

* <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"> v.8.10.2

### 형상 관리 전략

* <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Diagram

### Architecture

![Image](https://github.com/user-attachments/assets/9f7214d7-a167-4fc3-bb57-4171ded322eb)

### ERD

![Image](https://github.com/user-attachments/assets/7b8e7ce6-6a8e-42c5-9c59-0cf5094a010e)

## Features

* 회원 시스템
  * 회원가입
  * 로그인
  * 회원 검색
* 관리자 시스템(관리자 페이지)
  * 회원 관리
  * 게시글 관리
  * 강의 관리
  * 수강신청 관리
* 게시글 시스템
  * 게시글 CRUD
  * 게시글 검색
* 수강 신청 시스템
  * 강의 CRUD
  * 수강 신청/취소
  * 결제 상태 관리

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## RoadMap

- [x] DB 설계 개선
  - [x] ERD 수정
  - [x] JPA를 활용한 Entity 클래스 구현
  - [x] Spring Data JPA
  - [x] Q클래스를 생성해 QueryDsl 적용
![image](https://github.com/user-attachments/assets/46fee5db-628c-4a4a-9e38-c3007630f750)

- [x] Controller, Repository, DTO 세분화
![image (1)](https://github.com/user-attachments/assets/fbaf0794-d7e5-4f2e-8b7e-cf06976e6bbd)

- [x] Error Handler 개선
![image (2)](https://github.com/user-attachments/assets/df847d9a-2b75-47a7-a3ac-a1a469d21966)

- [x] 페이징 쿼리 개선
![image (3)](https://github.com/user-attachments/assets/d6d60c67-b3e4-4784-aeb3-c447eab119c4)

- [x] SQL Injection 문제 개선
- [x] REST API 생성
![image (4)](https://github.com/user-attachments/assets/f13e8ba1-33a1-4533-8069-2c59a98df138)

- [x] 테스트 코드 작성
![image (5)](https://github.com/user-attachments/assets/08d47ac3-bf95-4c4f-8ce0-f23ecbb33d1d)

- [x] log 분석 및 오류 해결
  - [x] 클래스 캐스팅 오류 (ClassCastException) -> 자바 버전, 톰캣 버전 안맞음 톰캣 10.1.17 버전으로 업그레이드
  - [x] request method 오류 -> 올바른 매핑 메소드로 변경
  - [x] enum, String type 오류 -> enum 타입으로 통일
  - [x] uri 인코딩 오류 -> spring boot utf-8로 설정 & uri 필터 추가
  - [x] 캐시 크기 문제 -> 캐시 크기 10MB -> 100MB 변경
  - [x] 애플리케이션이 종료될 때 특정 쓰레드(SeedGenerator Thread)를 종료하지 못하는 문제 -> Tomcat 종료시 강제로 스레드를 종료하는 코드 추가(contextDestroyed)
## Author

[@김진세] (https://github.com/in3kk)
