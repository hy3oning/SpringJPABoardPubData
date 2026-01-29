# 📝 JPA 기반 게시판 데이터베이스 설계 (Oracle)

이 저장소는 **Spring Boot + JPA(Hibernate)** 를 사용하여 게시판 CRUD를 구현하기 위한 **Oracle DB 실습용 프로젝트**입니다.
Java 및 데이터베이스 연동 구조를 학습하는 컴퓨터 전공자와 입문자를 대상으로, **JPA의 동작 원리와 DB 설계 흐름**를 이해하는 데 목적이 있습니다.

---

## 📌 프로젝트 개요

* 프로젝트명: Spring Boot JPA 게시판
* 목적: JPA 기반 CRUD 및 Oracle 시퀀스 연동 학습
* 특징:

  * 순수 JPA Repository 사용
  * Oracle 시퀀스 기반 PK 생성 전략
  * 기본적인 게시판 기능 구현

---

## 🕰️ 개발 기간

* 2026.01.29 ~ 2026.01.29

---

## ⚙️ 개발 환경

* 운영체제: Windows 11 Home
* IDE: IntelliJ IDEA / Eclipse
* JDK: OpenJDK 21.0.6
* Java: 21
* **Spring Boot: 3.2.x**
* Spring Framework: 6.x
* ORM: JPA (Hibernate 6.x)
* Database: Oracle 12c 이상
* 형상관리: Git, GitHub

---

## 📌 주요 기능

### 게시판 기능 (CRUD)

* 게시글 등록

  * 제목, 내용, 작성자 입력
* 게시글 수정

  * 제목 / 내용 / 작성자 수정
* 게시글 삭제

  * 게시글 번호 기준 삭제
* 게시글 목록 조회

  * 게시글 전체 조회
  * 최신글 기준 정렬
* 게시글 검색

  * 제목 또는 내용에 특정 키워드가 포함된 게시글 조회

---

## 🚀 데이터베이스 설정 (Oracle)

### 1️⃣ 사용자 생성

```sql
-- 스크립트 모드 활성화 (12c 이상)
ALTER SESSION SET "_ORACLE_SCRIPT" = true;

-- 기존 사용자 삭제
DROP USER KHH CASCADE;

-- 사용자 생성
CREATE USER KHH IDENTIFIED BY KHH
    DEFAULT TABLESPACE USERS
    TEMPORARY TABLESPACE TEMP;

-- 필수 권한 부여
GRANT CONNECT, RESOURCE TO KHH;
```

---

### 2️⃣ 테이블 생성

게시판의 핵심 정보를 저장하는 테이블입니다.

| 컬럼명     | 타입             | 제약조건            | 설명        |
| ------- | -------------- | --------------- | --------- |
| no      | NUMBER         | PK              | 게시글 고유 번호 |
| title   | VARCHAR2(100)  | NOT NULL        | 글 제목      |
| content | VARCHAR2(1000) |                 | 글 내용      |
| writer  | VARCHAR2(50)   | NOT NULL        | 작성자       |
| reg_date | DATE           | DEFAULT SYSDATE | 작성일       |

```sql
DROP TABLE jpa_board;

CREATE TABLE jpa_board (
    no NUMBER PRIMARY KEY,
    title VARCHAR2(100) NOT NULL,
    content VARCHAR2(1000),
    writer VARCHAR2(50) NOT NULL,
    regdate DATE DEFAULT SYSDATE
);
```

---

### 3️⃣ 시퀀스 생성

Oracle에서 게시글 번호 자동 증가를 위한 시퀀스입니다.

```sql
DROP SEQUENCE jpa_board_seq;

CREATE SEQUENCE jpa_board_seq
    START WITH 1
    INCREMENT BY 1;
```

---

## 🔍 JPA 사용 예시

### 데이터 등록

```java
Board board = boardRepository.save(board);
```

> `save()`는 식별자(PK) 존재 여부에 따라 insert 또는 update를 수행합니다.

---

### 전체 목록 조회 (내림차순)

```java
List<Board> boardList = boardRepository.findAll(
    Sort.by(Sort.Direction.DESC, "no")
);
```

---

### 게시글 수정

```java
Board board = boardRepository.getReferenceById(b.getNo());
board.setTitle(b.getTitle());
board.setContent(b.getContent());
board.setWriter(b.getWriter());
```

---

### 게시글 삭제

```java
boardRepository.deleteById(board.getNo());
```

---

### 게시글 검색

```java
boardRepository.findByContentContaining(keyword);
```

---

## ⚠️ 트랜잭션 주의사항

* 실습 중 데이터가 잘못 입력되었을 경우:

```sql
ROLLBACK;
```

* 데이터 확정 시:

```sql
COMMIT;
```

---

## 📎 참고 사항

* 본 프로젝트는 **학습 목적**으로 작성되었습니다.
* 실무 환경에서는 권한 관리, 예외 처리, 트랜잭션 범위 설정이 추가로 필요합니다.
* Oracle 시퀀스와 JPA `@SequenceGenerator` 매핑 구조를 이해하는 데 중점을 둡니다.

---

## ✅ 정리

이 프로젝트를 통해 다음을 학습할 수 있습니다.

* Spring Boot 3.x 기반 JPA 구조 이해
* Oracle 시퀀스와 엔티티 식별자 매핑
* JPA Repository를 활용한 CRUD 구현
* 기본적인 게시판 도메인 설계
