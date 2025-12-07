# OKBO 프론트엔드

OKBO 백엔드 API를 테스트하기 위한 간단한 프론트엔드 애플리케이션입니다.

## 📁 프로젝트 구조

```
frontend/
├── index.html              # 로그인 페이지
├── signup.html             # 회원가입 페이지
├── main.html               # 메인 페이지 (게시글 목록)
├── board-detail.html       # 게시글 상세 페이지
├── board-create.html       # 게시글 작성 페이지
├── board-edit.html         # 게시글 수정 페이지
├── mypage.html             # 마이페이지
├── css/
│   └── style.css          # 공통 스타일
└── js/
    ├── api.js             # API 호출 공통 함수
    └── auth.js            # 인증 관련 유틸리티
```

## 🚀 실행 방법

### 1. 백엔드 서버 실행

먼저 Spring Boot 백엔드를 실행하세요:

```bash
./gradlew bootRun
```

백엔드는 `http://localhost:8080`에서 실행됩니다.

### 2. 프론트엔드 실행

프론트엔드는 정적 HTML 파일이므로 다음 방법 중 하나를 선택하여 실행할 수 있습니다:

#### 방법 1: Live Server (VS Code 확장)
1. VS Code에서 `Live Server` 확장 설치
2. `index.html` 파일에서 우클릭 → "Open with Live Server"

#### 방법 2: Python 간단한 서버
```bash
# 프로젝트 루트 디렉토리에서
python3 -m http.server 8000
```

그리고 브라우저에서 `http://localhost:8000/frontend/index.html` 접속

#### 방법 3: Node.js http-server
```bash
# http-server 설치 (한 번만)
npm install -g http-server

# 프로젝트 루트 디렉토리에서
http-server -p 8000
```

그리고 브라우저에서 `http://localhost:8000/frontend/index.html` 접속

## 🎯 주요 기능

### 1. 회원 관리
- ✅ 회원가입 (닉네임, 이메일, 비밀번호, 응원 구단)
- ✅ 로그인 (JWT 토큰 기반)
- ✅ 로그아웃
- ✅ 내 정보 조회
- ✅ 닉네임 변경
- ✅ 비밀번호 변경
- ✅ 회원 탈퇴

### 2. 게시글 관리
- ✅ 게시글 전체 조회 (페이지네이션)
- ✅ 구단별 게시글 조회
- ✅ 팔로우한 사람들의 게시글 조회
- ✅ 게시글 검색 (제목, 작성자, 날짜)
- ✅ 게시글 상세 조회
- ✅ 게시글 작성
- ✅ 게시글 수정
- ✅ 게시글 삭제
- ✅ 게시글 좋아요

### 3. 댓글 관리
- ✅ 댓글 조회 (페이지네이션)
- ✅ 댓글 작성
- ✅ 댓글 수정
- ✅ 댓글 삭제
- ✅ 댓글 좋아요

### 4. 팔로우 관리
- ✅ 팔로우/언팔로우
- ✅ 팔로잉/팔로워 수 조회
- ✅ 팔로잉 목록 조회
- ✅ 팔로워 목록 조회

## 🔧 설정

### API Base URL 변경

`frontend/js/api.js` 파일에서 API 서버 주소를 변경할 수 있습니다:

```javascript
const API_BASE_URL = 'http://localhost:8080';
```

## 📝 사용 흐름

1. **회원가입**: `signup.html`에서 계정 생성
2. **로그인**: `index.html`에서 로그인 (JWT 토큰이 localStorage에 저장됨)
3. **메인 페이지**: 게시글 목록 확인, 검색, 필터링
4. **게시글 작성**: 글쓰기 버튼으로 새 게시글 작성
5. **게시글 상세**: 게시글 클릭 시 상세 내용, 댓글 확인
6. **마이페이지**: 프로필 수정, 팔로우 관리, 내 게시글 확인

## ⚠️ 주의사항

1. **CORS 설정**: 백엔드에서 CORS가 허용되어야 합니다.
2. **JWT 토큰**: localStorage에 저장되므로 보안에 주의하세요.
3. **브라우저**: 최신 버전의 Chrome, Firefox, Safari, Edge 사용 권장

## 🐛 문제 해결

### API 호출 실패
- 백엔드 서버가 실행 중인지 확인
- 브라우저 콘솔에서 에러 메시지 확인
- CORS 설정 확인

### 로그인 안 됨
- 이메일과 비밀번호 확인
- 백엔드 로그 확인
- 브라우저 localStorage 확인

### 페이지 로딩 안 됨
- 파일 경로 확인 (`/frontend/...`)
- 서버가 프로젝트 루트에서 실행되는지 확인

## 🎨 UI 커스터마이징

`frontend/css/style.css` 파일을 수정하여 디자인을 변경할 수 있습니다.

## 📌 향후 개선 사항

- [ ] 프로필 이미지 업로드
- [ ] 게시글 이미지 첨부
- [ ] 실시간 알림
- [ ] 무한 스크롤
- [ ] 다크 모드
- [ ] 반응형 디자인 개선
