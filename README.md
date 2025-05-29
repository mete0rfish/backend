# 나만의 건축사수, 3D 모델 플랫폼 원툴

![image](https://github.com/user-attachments/assets/2c54c286-7417-4123-add6-fffb97cbd1f0)


## 소개

저희 팀 원툴은 3D 도면 모델을 주로 사용하는 건축업계 종사자들을 위한 도면 서비스를 제공합니다.

1. 필요한 도면만 빠르게
2. 도면 구매를 보다 간편하게
3. 내가 만든 도면 판매를 안전하게

현재 건축 업계에선 아래와 같은 문제점이 있어요.

- 건축 업계에서의 도면 유통 한계
- 교육용 도면 배포 환경 구축

팀 원툴은 건축 업계의 도면 사용을 활성화하기 위해 노력하고 있습니다.

<!-- 기대 효과, 동기 -->

## 개요

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

## 활용 기술 / 기술적 의사 결정

| **요구 사항** | **선택지** | **기술 선택 이유** |
| ----- | ------ | ----------- |
| **💽데이터베이스** | 1. MySQL <br/> 2. PostgreSQL <br/> 3. MariaDB <br/> 4. MongoDB <br/> 5. Redis | - 도면 목록 및 검색 최적화를 위해 인덱스, 캐시, 버퍼 풀 등의 성능 최적화 기능을 제공하는 MySQL 사용 <br/> - 결제 및 구매 내역와 같은 중요한 데이터를 위한 InnoDB의 ACID 트랜잭션 사용 <br/> - Redis의 인메모리 데이터 저장 방식으로 토큰 조회 및 삽입 성능 개선 <br/> - 인증 코드의 자동 삭제를 위한 Redis의 TTL 기능 사용 |
| **🖥️모니터링** | 1. Jmeter <br/> 2. Prometheus <br/> 3. AWS CloudWatch | - 설치 용이성과 비용적 장점을 가진 Prometheus 사용 <br/> - Expert 설치를 통한 풀링 방식으로 메트릭 수집이 용이 |
| **🛠️CI/CD** | 1. Github Actions <br/> 2. Jenkins | - 별도의 서버 구축이 필요없는 Github Actions 사용 <br/> - YAML 기반 간편하고 쉬운 스크립트 작성 |

<!-- DB, 부하테스트, 모니터링, CICD, 검색기능개선(동적쿼리, 인덱스 등등) -->

## 아키텍처

### 인프라 아키텍처

![다이어그램-01 - redis drawio](https://github.com/user-attachments/assets/d7695b56-334a-4164-8452-50f9217d41fc)

### 레이어드 아키텍처

![image](https://github.com/user-attachments/assets/1ffa3146-0f79-43b5-9278-6b8e69b80cec)


## ERD, 유저 시나리오

### ERD

![OneTool](https://github.com/user-attachments/assets/1436a9f7-42ad-4601-8e1d-9bf97551fbdc)

### 유저 시나리오

[🔗Notion: 유저 시나리오](https://garrulous-bearskin-817.notion.site/66ed82a478514cd5ae13836b30e2909e?pvs=4)

## 트러블 슈팅 및 개발 기록
<!-- 도면 구매, 도면 검색, 도면 업로드 -->


## 성능 개선



## 팀원

|역할|github|이름|담당|
|---|---|---|---|
| 팀장 |  | 정재민 ||
| 팀원 | [<img src="https://avatars.githubusercontent.com/u/105264785?v=4" height=100 width=100>](https://github.com/tnqkr3494) | 강인권 | |
| 팀원 | [<img src="https://avatars.githubusercontent.com/u/123933574?v=4" height=100 width=100>](https://github.com/LEEDONGH00N) | 이동훈 ||
| 팀원 | [<img src="https://avatars.githubusercontent.com/u/63222221?v=4" height=100 width=100> ](https://github.com/mete0rfish)| 윤성원 | API 개발 <br/> DB 설계 및 구축 <br/> 인프라 및 CI/CD 설계 및 구축 <br/> 코드 리펙터링 |
| 팀원 | [<img src="https://avatars.githubusercontent.com/u/92675692?v=4" height=100 width=100> ](https://github.com/day024) | 정다영 ||
| 팀원 | [<img src="https://avatars.githubusercontent.com/u/164465431?v=4" height=100 width=100> ](https://github.com/PlusUltraCode) | 이동호 | 코드 리펙터링 |

