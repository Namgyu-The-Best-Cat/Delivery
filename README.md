# 배달 및 포장 음식 주문 관리 플랫폼

## 프로젝트 개요

- **주제:** 배달 및 포장 음식 주문 관리 플랫폼 개발
- **목표:** 광화문 근처에서 운영될 음식점들의 배달 및 포장 주문 관리, 결제, 그리고 주문 내역 관리 기능을 제공하는 플랫폼 개발
---

## 팀원 역할분담

| 팀원 이름 | 역할 | 담당 기능 |
| --------- | ---- | --------- |
| 고경호  | 팀장, 백엔드 개발자 | 전체 프로젝트 관리, AI API, 리뷰 API, 메뉴 API |
| 양혜지    | 백엔드 개발자   | 주문 관리, 결제 시스템, 지역 API, 카테고리 API, 가게 API |
| 최영근    | 백엔드 개발자 | 인증/인가, 로그인 및 회원가입, 관리자 API|
| 하남규    | 백엔드 개발자 | 주문API, 결제 API |

---

## 서비스 구성

### 주요 기능

1. **음식점 분류**
    - **카테고리:** 한식, 중식, 분식, 치킨, 피자
    - **확장성:** 유연한 데이터 구조로 카테고리 추가/수정 가능

2. **결제 시스템**
    - **결제 방식:** 카드 결제
    - **PG사 연동:** 외주 개발로 진행, 결제 내역만 데이터베이스에 저장
    - **결제 테이블:** 결제 내역 전용 테이블 설계

3. **주문 관리**
    - **주문 취소:** 생성 후 5분 이내 취소 가능
    - **주문 유형:** 온라인 주문 및 대면 주문 지원
    - **대면 주문 처리:** 가게 사장님이 직접 접수

4. **데이터 보존 및 삭제 처리**
    - **데이터 보존:** 완전 삭제 대신 숨김 처리
    - **상품 숨김:** 개별 상품도 숨김 가능
    - **데이터 감사 로그:** 생성일, 생성 아이디, 수정일, 수정 아이디, 삭제일, 삭제 아이디 포함

5. **접근 권한 관리**
    - **고객:** 자신의 주문 내역만 조회 가능
    - **가게 주인:** 자신의 가게 주문 내역, 정보, 주문 처리 및 메뉴 수정 가능
    - **관리자:** 모든 가게 및 주문에 대한 전체 권한

6. **배송지 정보**
    - **필수 입력 사항:** 주소지, 요청 사항
    - **적용 범위:** 주문 및 배달 정보

7. **AI API 연동**
    - **상품 설명 자동 생성:** AI API로 상품 설명 작성 지원
    - **AI 요청 기록:** 질문과 대답 모두 데이터베이스에 저장

---

## 기술 스택

- **백엔드:**
  <div>
    <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java">
    <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot">
  </div>
  <div>
     <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white" alt="Spring Security">
    <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white" alt="JWT">
    <img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=hibernate&logoColor=white" alt="JPA">
    <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white" alt="Hibernate">
  </div>
  
- **데이터베이스:**
  <div>
        <img src="https://img.shields.io/badge/PostgreSQL-336791?style=flat&logo=postgresql&logoColor=white" alt="PostgreSQL">
  </div>
  
- **인프라:**
   <div>
     <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="PostgreSQL">
     <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=amazonwebservices&logoColor=white" alt="PostgreSQL">
     <img src="https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=github-actions&logoColor=white" alt="PostgreSQL">
   </div>

## ERD

![ERD Diagram](https://github.com/user-attachments/assets/9ed12f97-d4b9-47e9-b53b-3d12cb02669a)

---


## 프로젝트 목적 및 상세

본 프로젝트는 광화문 근처에서 운영되는 음식점들을 대상으로 배달 및 포장 주문 관리, 결제, 주문 내역 관리 기능을 제공하는 플랫폼을 개발하는 것을 목표로 합니다. 주요 기능은 다음과 같습니다:

- **효율적인 주문 관리:**
  - 음식점은 플랫폼을 통해 주문을 실시간으로 관리하고, 주문 취소는 생성 후 5분 이내에만 가능하여 운영 효율성을 높입니다.
- **안전한 결제 시스템:**
  - 카드 결제를 지원하며, 외주 개발을 통해 PG사와의 연동을 처리하여 보안과 안정성을 확보합니다.
- **데이터 보존 및 감사 로그:**
  - 모든 데이터는 완전 삭제되지 않고 숨김 처리되며, 감사 로그를 통해 데이터의 변경 이력을 관리합니다.
- **유연한 접근 권한:**
  - 고객, 가게 주인, 관리자가 각기 다른 권한을 가지고 플랫폼을 안전하게 이용할 수 있습니다.
- **AI 기반 기능:**
  - AI API를 연동하여 음식점 사장님이 쉽게 상품 설명을 생성할 수 있도록 지원합니다.

초기에는 광화문 근처로 한정하여 운영하지만, 향후 지역 분류 시스템을 통해 다양한 지역으로 확장할 수 있도록 설계되었습니다.

---

## API 문서

API 문서는 [API Docs](https://www.notion.so/teamsparta/API-e58c03f93fce4e159679499a227afafd)에서 확인할 수 있습니다.

