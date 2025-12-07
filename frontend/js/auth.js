// 로그인 여부 체크
function isLoggedIn() {
    return localStorage.getItem('token') !== null;
}

// 토큰 저장
function saveToken(token) {
    if (!token || token === 'undefined' || typeof token !== 'string') {
        console.error('유효하지 않은 토큰:', token);
        return;
    }
    localStorage.setItem('token', token);
}

// 토큰 가져오기
function getToken() {
    return localStorage.getItem('token');
}

// 로그아웃
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('mock_user'); // 데모 모드 사용자 정보 제거
    if (typeof CURRENT_USER !== 'undefined') {
        CURRENT_USER = null; // Mock API 사용자 상태 초기화
    }
    window.location.href = './index.html';
}

// 로그인 필요 페이지 보호
function requireAuth() {
    if (!isLoggedIn()) {
        alert('로그인이 필요합니다.');
        window.location.href = './index.html';
    }
}

// 헤더 업데이트 (로그인 상태에 따라)
function updateHeader() {
    const nav = document.querySelector('nav');
    if (!nav) return;

    if (isLoggedIn()) {
        nav.innerHTML = `
            <a href="./mypage.html">마이페이지</a>
            <a href="#" onclick="logout()">로그아웃</a>
        `;
    } else {
        nav.innerHTML = `
            <a href="./index.html">로그인</a>
            <a href="./signup.html">회원가입</a>
        `;
    }
}

// 페이지 로드 시 헤더 업데이트
document.addEventListener('DOMContentLoaded', updateHeader);
