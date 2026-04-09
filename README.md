### 📌 프로젝트 소개

- 팀스파르타 내일배움캠프의 실무형 Kotlin & Spring 개발자 양성과정 중 진행된 팀 프로젝트

- 기간 : 2025년 11월 21일(금) ~ 28일(금)
  
- 주제 : Spring Boot에서 JPA와 JWT에 대한 이해를 바탕으로, 실제로 사용될 수 있는 뉴스피드 구현
  
- 배포 링크 : [OKBO](https://bellalee0.github.io/sparta_teamprj_OKBO/frontend/main.html)
  
  테스트 방법 : 구단별 1명의 사용자가 만들어져 있기 때문에, 원하는 팀 선택하여 로그인 후 테스트 진행
  
  ID: `[팀]@test.com` 예시: `kt@test.com` | PW: `1234`
  
  (팀 : doosan, hanwha, kia, kt, kiwoom, lg, lotte, nc, samsung, ssg)

- 프로젝트 회고 : [✍️ velog](https://velog.io/@bella0/%EB%89%B4%EC%8A%A4%ED%94%BC%EB%93%9C-%ED%8C%80-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9A%8C%EA%B3%A0)

## ⚾️ OKBO – KBO 야구 팬들을 위한 커뮤니티 플랫폼

### “흩어져 있던 야구 기록과 팬심을 한곳에!”

나만의 야구 일지를 기록하고, 같은 팀을 응원하는 팬들과 실시간으로 소통하는 야구 커뮤니티 플랫폼입니다.

### 📌 OKBO 프로젝트 목적

야구가 취미이지만

> 정보가 여러 곳에 흩어져 있고,
> 
> 팬들과 소통할 공간이 부족하고,
> 
> 나만의 기록을 정리하기 어려웠던 사용자들을 위해

취미 생활을 더 재미있고, 더 깊게 즐길 수 있는 커뮤니티 환경을 목표로 합니다.

**같은 팀을 응원하는 사람과 연결되고, 기록하고, 공유할 수 있는 플랫폼을 제공**하는 것이 OKBO의 핵심 목적입니다.


### 🧩 핵심 기능

**1. 나의 구단 선택**
- [x] 최초 가입 시 응원하는 구단 선택
- [x] 선택한 구단을 기반으로 홈 피드 구성

**2. 게시글 작성**
- [x] 경기 후기, 응원 메시지, 분석 등 원하는 내용 작성

**3. 구단별 게시글 조회**
- [x] 특정 구단 팬들이 올린 게시글을 모아서 조회
- [x] 구단 중심의 커뮤니티 공간 제공

**4. 친구 팔로우**
- [x] 마음에 드는 사용자를 팔로우

**5. 팔로우한 사람들의 게시글 조회**
- [x] 개인화된 피드 제공

### 👩🏻‍💻 내가 담당한 기능

**1. 팔로우 시스템**

- 팔로우/언팔로우 API
- 팔로워/팔로잉 목록 조회
- Follow 엔티티 설계 (from_user, to_user)

**2. JWT 인증 시스템**

- 세션 → JWT 전환
- JwtUtil 클래스 구현
- JWT Filter 구현 및 적용
- 토큰 검증 로직

**3. 게시글 정렬 기능**

- Pageable 활용 동적 정렬
- 최신순/제목순/좋아요순/댓글순 정렬

### 🛠️ 사용 기술

`Java 17` `Spring Boot 3.5.8` `MySQL 8.4` `IntelliJ version 25.2.2` `Postman`


## 🧱 설계

### 📝 와이어프레임

[와이어프레임 by draw.id](https://drive.google.com/file/d/1ribiYon1CsOWFij3WowWcq9C-Eb3nI2E/view?usp=sharing)


### 📝 API 명세서

[API 명세서 by Notion](https://road-cartoon-1a1.notion.site/2b91d6e7a68c8157b58dd59b83f8306a?v=2b91d6e7a68c81d8bfa7000cfce56d45&source=copy_link)


### 📝 ERD

[ERD 명세서 by Notion](https://road-cartoon-1a1.notion.site/ERD-2b91d6e7a68c81ac980af85dc8a51d31?source=copy_link)


### 📁 프로젝트 구조 (Directory Structure)
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


## 💻 개인 Dev - Front-end 개발

※ 본 과정은 Back-end 개발자 양성 과정으로 Front-end 개발은 진행되지 않았으며, 팀 프로젝트가 완료된 이후 개인적으로 추가한 작업입니다.
  
※ 기존에 작업한 와이어프레임을 바탕으로 **Claude Code를 활용한 바이브 코딩으로 구현**하였습니다.

기술 스택: `HTML` `CSS` `JavaScript`

### 📁 프로젝트 구조
```
frontend/
├── index.html              # 로그인 페이지
├── signup.html             # 회원가입 페이지
├── main.html               # 메인 페이지 (게시글 목록)
├── board-detail.html       # 게시글 상세 페이지
├── board-create.html       # 게시글 작성 페이지
├── board-edit.html         # 게시글 수정 페이지
├── mypage.html             # 마이페이지
├── user-profile.html       # 다른 유저 프로필 페이지
├── css/
│   ├── style.css           # 공통 스타일
│   └── team-theme.css      # 구단별 테마 색상
└── js/
    ├── api.js              # API 호출 공통 함수
    └── auth.js             # 인증 관련 유틸리티
```

### ✨ 페이지별 상세 기능

| 페이지 |	주요 기능 |
|:---|:---|
| index.html | 로그인 폼, 이메일/비밀번호 검증, JWT 토큰 획득 및 저장 |
| signup.html | 회원가입 폼, 10개 구단 선택 드롭다운, 입력값 검증 |
| main.html | 게시글 목록 테이블, 전체/팔로잉 토글, 구단 필터, 검색, 정렬, 페이지네이션 |
| board-detail.html | 게시글 상세 내용, 작성자 정보, 좋아요, 댓글 CRUD, 수정/삭제 버튼 (작성자만) |
| board-create.html | 게시글 작성 폼, 제목/내용 입력, 구단 선택, 작성 완료 후 상세 페이지 이동 |
| board-edit.html | 게시글 수정 폼, 기존 내용 로드, 제목/내용 수정 (구단은 변경 불가) |
| mypage.html | 내 프로필 조회, 닉네임/비밀번호 변경, 회원 탈퇴, 내가 쓴 글 목록, 팔로잉/팔로워 관리 |
| user-profile.html | 다른 유저 정보 조회, 팔로우/언팔로우, 해당 유저가 작성한 게시글 목록 |


## 🚀 실행 방법

**1. 백엔드 실행**

```
./gradlew bootRun
```

**2. 프론트엔드 실행** (3가지 방법 중 선택)

- VS Code Live Server: `index.html` 우클릭 → Open with Live Server

- Python 서버: `python3 -m http.server 8000` (루트 디렉토리에서)

- Node.js http-server: `http-server -p 8000` (루트 디렉토리에서)

**3. 브라우저 접속**

http://localhost:8000/frontend/main.html

**⚠️ 주의사항**

- CORS 설정이 백엔드에 적용되어야 정상 작동
- JWT 토큰은 LocalStorage에 저장 (보안 주의)
- 최신 브라우저 사용 권장 (Chrome, Firefox, Safari, Edge)

## 👥 팀 소개 – 5조 오버플로우(Overflow)

### “아이디어와 열정이 넘쳐흘러 곧바로 코드로 구현합니다.”

팀장

- 김현수 : 팀 Enum 관리, 전역 예외 처리, 유저 CRUD 구현, 검색 기능 구현

팀원

- 윤지현	: 유저 CRUD 구현, 좋아요 CRUD 구현

- 이서연	: 팔로우 CRUD 구현, 정렬 기능 구현, JWT 적용

- 이세진	: 게시글 CRUD 구현, 댓글 CRUD 구현

- 최승희	: 게시글 CRUD 구현, 댓글 CRUD 구현


### 안내

본 프로젝트는 팀스파르타 내일배움캠프의 실무형 Kotlin & Spring 개발자 양성과정 중 팀 Overflow의 학습 및 포트폴리오 목적으로 제작되었습니다.
백엔드 개발은 팀 Overflow에서 진행하였으며, 프론트엔드 개발은 개인의 흥미로 Claude Code를 사용한 바이브 코딩으로 진행되었습니다.
