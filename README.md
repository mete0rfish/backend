# 🏗️ 나만의 건축사수, 3D 모델 플랫폼 원툴

![image](https://github.com/user-attachments/assets/2c54c286-7417-4123-add6-fffb97cbd1f0)


## 📌 소개

저희 팀 원툴은 3D 도면 모델을 주로 사용하는 건축업계 종사자들을 위한 도면 서비스를 제공합니다.

1. 필요한 도면만 빠르게
2. 도면 구매를 보다 간편하게
3. 내가 만든 도면 판매를 안전하게

현재 건축 업계에선 아래와 같은 문제점이 있어요.

- 건축 업계에서의 도면 유통 한계
- 교육용 도면 배포 환경 구축

팀 원툴은 건축 업계의 도면 사용을 활성화하기 위해 노력하고 있습니다.

<!-- 기대 효과, 동기 -->

<br/>

## 🔖 개요

- 프로젝트 이름: 원툴 Onetool
- 개발 기간: 2024.03 ~ 2024.12
- 성과

| 이름 | 성과 | 일시 |
| --- | --- | --- |
| 세종대캠퍼스타운 창업아이디아톤 | 대상 | 2024.07.31 |
| 세종대학교 IT컨퍼런스 | 우수상 (5/15팀) | 2024.09.26 |
| 세종대 캠퍼스타운 창업세미나 모의 IR | 우수상 (2등/50팀) | 2024.09.11 |
| 세종대 피칭&멘토링대회 | 우수상 (3등/6명) | 2024.09.11 |
| 세종대 창업 컨설팅 | 수료 | 2024.08.14 |

<!-- ## (실제 기능 움짤) -->

<br/>

## 🛠️ 기술

### 사용된 기술
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white"> 

<img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=Redis&logoColor=white">  <img src="https://img.shields.io/badge/nginx-%23009639.svg?style=for-the-badge&logo=nginx&logoColor=white">

<img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/GitHub%20Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=fff&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white"> <img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=Prometheus&logoColor=white"> <img src="https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=white">


### 기술적 의사 결정

| **요구 사항** | **선택지** | **기술 선택 이유** |
| ----- | ------ | ----------- |
| **💽데이터베이스** | 1. MySQL <br/> 2. PostgreSQL <br/> 3. MariaDB <br/> 4. MongoDB <br/> 5. Redis | - 도면 목록 및 검색 최적화를 위해 인덱스, 캐시, 버퍼 풀 등의 성능 최적화 기능을 제공하는 MySQL 사용 <br/> - 결제 및 구매 내역와 같은 중요한 데이터를 위한 InnoDB의 ACID 트랜잭션 사용 <br/> - Redis의 인메모리 데이터 저장 방식으로 토큰 조회 및 삽입 성능 개선 <br/> - 인증 코드의 자동 삭제를 위한 Redis의 TTL 기능 사용 |
| **🖥️모니터링** | 1. Jmeter <br/> 2. Prometheus <br/> 3. AWS CloudWatch | - 설치 용이성과 비용적 장점을 가진 Prometheus 사용 <br/> - Expert 설치를 통한 풀링 방식으로 메트릭 수집이 용이 |
| **🛠️CI/CD** | 1. Github Actions <br/> 2. Jenkins | - 별도의 서버 구축이 필요없는 Github Actions 사용 <br/> - YAML 기반 간편하고 쉬운 스크립트 작성 |

**[🛠️ 기술적 의사 결정 문서](https://acoustic-rest-b1b.notion.site/20964b4a4ab480abb4c7c2b6b1f2d75c?source=copy_link)**

<!-- DB, 부하테스트, 모니터링, CICD, 검색기능개선(동적쿼리, 인덱스 등등) -->

<br/>

## 🖼️ 아키텍처

### 인프라 아키텍처

![다이어그램-01 - redis drawio](https://github.com/user-attachments/assets/d7695b56-334a-4164-8452-50f9217d41fc)

### 레이어드 아키텍처

![image](https://github.com/user-attachments/assets/1ffa3146-0f79-43b5-9278-6b8e69b80cec)

<br/>

## 🗂️ ERD, 유저 시나리오

### ERD

![OneTool](https://github.com/user-attachments/assets/1436a9f7-42ad-4601-8e1d-9bf97551fbdc)

### 유저 시나리오

[🔗Notion: 유저 시나리오](https://garrulous-bearskin-817.notion.site/66ed82a478514cd5ae13836b30e2909e?pvs=4)

<br/>

## ☑️ 트러블 슈팅 및 개발 기록
<!-- 도면 구매, 도면 검색, 도면 업로드 -->
**[🛠️ 트러블 슈팅 목록](https://acoustic-rest-b1b.notion.site/5942ba02109f4cb29281de29b12d9775?v=25e8e1693b8a4bef85f4d83d91342768&source=copy_link)**

<br/>

## 🔥 성능 개선
1. Fake 기법을 통해 테스트 속도 개선 ([🔗PR#209](https://github.com/likelion-onetool/backend/pull/209))
    - 빈 컨텍스트 사용 대신 Fake를 이용하여 컨텍스트 멤버 생성 기능의 테스트 속도를 990ms에서 460ms로 `46%` 향상
    - 테스트의 대부분을 차지하는 단위 테스트의 수행 속도를 평균 `36%` 개선
2. 커버링 인덱스로 검색 쿼리 속도 향상([🔗PR#182](https://github.com/likelion-onetool/backend/pull/182))
   - 커버링 인덱스를 이용해 검색 기능의 수행 시간이 637ms에서 473ms로 약 `34%` 향상
   - Count 쿼리의 인덱스 사용 시, 미비한 차이로 인한 불필요한 인덱스 사용 방지
3. N+1 문제 해결로 실행 쿼리 감소 ([🔗PR#115](https://github.com/likelion-onetool/backend/pull/115))
   - 다대일 관계의 적은 중복 문제 해결을 위해 inner join을 사용하여 키워드 검색 쿼리를 6개 → 4개 감소

<br/>

## 👥 컨트리뷰터

| 역할    |github|이름|
|-------|---|---|
| Front | [<img src="https://avatars.githubusercontent.com/u/105264785?v=4" height=100 width=100>](https://github.com/tnqkr3494) | 강인권 |
| Back  | [<img src="https://avatars.githubusercontent.com/u/123933574?v=4" height=100 width=100>](https://github.com/LEEDONGH00N) | 이동훈 |
| Back  | [<img src="https://avatars.githubusercontent.com/u/63222221?v=4" height=100 width=100> ](https://github.com/mete0rfish)| 윤성원 |
| Back  | [<img src="https://avatars.githubusercontent.com/u/92675692?v=4" height=100 width=100> ](https://github.com/day024) | 정다영 |
| Back  | [<img src="https://avatars.githubusercontent.com/u/164465431?v=4" height=100 width=100> ](https://github.com/PlusUltraCode) | 이동호 |

