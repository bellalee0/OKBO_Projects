# OKBO – KBO 야구 팬들을 위한 커뮤니티 플랫폼


### “흩어져 있던 야구 기록과 팬심을 한곳에!”

나만의 야구 일지를 기록하고, 같은 팀을 응원하는 팬들과 실시간으로 소통하는 야구 커뮤니티 플랫폼입니다.


## 프로젝트 목적

야구가 취미이지만

- 정보가 여러 곳에 흩어져 있고
- 팬들과 소통할 공간이 부족하고
- 나만의 기록을 정리하기 어려웠던 사용자들을 위해

취미 생활을 더 재미있고, 더 깊게 즐길 수 있는 커뮤니티 환경을 목표로 합니다.

같은 팀을 응원하는 사람과 연결되고, 기록하고, 공유할 수 있는 플랫폼을 제공하는 것이 OKBO의 핵심 목적입니다.


## 핵심 기능

1. 나의 구단 선택
   - 최초 가입 시 응원하는 구단 선택
   - 선택한 구단을 기반으로 홈 피드 구성

2. 게시글 작성
   - 경기 후기, 응원 메시지, 분석 등 원하는 내용 작성

3. 구단별 게시글 조회
   - 특정 구단 팬들이 올린 게시글을 모아서 조회
   - 구단 중심의 커뮤니티 공간 제공

4. 친구 팔로우
   - 마음에 드는 사용자를 팔로우

5. 팔로우한 사람들의 게시글 조회
   - 개인화된 피드 제공


## 기술 스택 (Tech Stack)

- Java: 17
- Spring Boot: 3.5.8
- MySQL: 8.4
- IntelliJ version: 25.2.2
- API Test: Postman


## 프로젝트 구조 (Directory Structure)
```
OKBO_Projects/
├── src/
│   ├── main/java/
│   │   └── com/okbo_projects/
│   │       ├── common/
│   │       |  ├── entity
│   │       │  ├── exception
│   │       │  ├── filter
│   │       │  ├── model
│   │       │  └── utils
│   │       │
│   │       └── domain
│   │          ├── board
│   │          ├── comment
│   │          ├── follow
│   │          ├── like
│   │          └── user
│   │
│   └── resources/
│       └── application.yml
├── build.gradle
└── README.md
```

각 도메인은 controller, service, repository 계층으로 분리되어 있고, model 패키지에서 dto, request, response 객체들을 관리합니다.
- board: 게시판 기능
- comment: 댓글 기능
- follow: 팔로우 기능
- like: 좋아요 기능
- user: 사용자 관리


## API 명세서

<details>
<summary>유저</summary>
  <img width="1754" height="960" alt="유저api" src="https://github.com/user-attachments/assets/97bce951-d729-4573-81c0-29d5728cc265" />
</details>

<details>
<summary>팔로우</summary>
  <img width="1754" height="633" alt="팔로우api" src="https://github.com/user-attachments/assets/c6e3f3b6-229d-45f9-9b08-4fdf3ab539da" />
</details>

<details>
<summary>게시글</summary>
  <img width="1754" height="2011" alt="게시글 api" src="https://github.com/user-attachments/assets/811386a7-b203-496c-82c3-e847253e4f6f" />
</details>

<details>
<summary>댓글</summary>
  <img width="1756" height="777" alt="댓글api" src="https://github.com/user-attachments/assets/dffcfca7-b258-4f7b-85b6-766229b9b77a" />
</details>

<details>
<summary>좋아요</summary>
  <img width="1753" height="614" alt="좋아요api" src="https://github.com/user-attachments/assets/9811a1aa-13ab-4909-8a07-cd57d49d9cd3" />
</details>


## ERD

<img width="1345" height="781" alt="image" src="https://github.com/user-attachments/assets/7986da55-46ae-44d8-8285-9fd9cc6cbe50" />



## 팀 소개 – 5조 오버플로우(Overflow)

### “아이디어와 열정이 넘쳐흘러 곧바로 코드로 구현합니다.”

팀장

- 김현수 : 팀 Enum 관리, 전역 예외 처리, 유저 CRUD 구현, 검색 기능 구현

팀원

- 윤지현	: 유저 CRUD 구현, 좋아요 CRUD 구현

- 이서연	: 팔로우 CRUD 구현, 정렬 기능 구현, JWT 적용

- 이세진	: 게시글 CRUD 구현, 댓글 CRUD 구현

- 최승희	: 게시글 CRUD 구현, 댓글 CRUD 구현


## 라이선스

본 프로젝트는 팀스파르타 내일배움캠프의 실무형 Kotlin & Spring 개발자 양성과정 중 팀 Overflow의 학습 및 포트폴리오 목적으로 제작되었습니다.
