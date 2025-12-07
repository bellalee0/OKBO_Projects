// API Base URL
const API_BASE_URL = 'http://localhost:8080';

// API 호출 공통 함수
async function apiCall(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;

    // 기본 헤더 설정
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    // JWT 토큰이 있으면 Authorization 헤더 추가
    const token = localStorage.getItem('token');
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        ...options,
        headers
    };

    try {
        const response = await fetch(url, config);

        // 204 No Content인 경우
        if (response.status === 204) {
            return { success: true };
        }

        // 401 Unauthorized인 경우
        if (response.status === 401) {
            const errorData = await response.json();

            // 비밀번호 검증이 필요한 엔드포인트들 (로그인, 비밀번호 변경, 회원 탈퇴)
            // 이 경우 에러를 던져서 각 페이지의 catch 블록에서 처리하도록 함
            if (endpoint === '/users/login' ||
                endpoint === '/users' ||
                endpoint === '/users/password') {
                throw new Error(errorData.message || '비밀번호가 올바르지 않습니다.');
            }

            // 다른 API의 경우 (인증 토큰 문제) 토큰 삭제하고 로그인 페이지로 리다이렉트
            alert('로그인이 필요합니다.');
            localStorage.removeItem('token');
            window.location.href = '/frontend/index.html';
            return;
        }

        // 응답이 성공이 아닌 경우
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || '요청에 실패했습니다.');
        }

        // Content-Type 확인하여 JSON 또는 텍스트로 파싱
        const contentType = response.headers.get('content-type');

        if (contentType && contentType.includes('application/json')) {
            return await response.json();
        } else {
            // JSON이 아닌 경우 (예: 로그인 토큰) 텍스트로 반환
            return await response.text();
        }
    } catch (error) {
        console.error('API 호출 에러:', error);
        throw error;
    }
}

// GET 요청
async function apiGet(endpoint) {
    return apiCall(endpoint, {
        method: 'GET'
    });
}

// POST 요청
async function apiPost(endpoint, data) {
    return apiCall(endpoint, {
        method: 'POST',
        body: JSON.stringify(data)
    });
}

// PUT 요청
async function apiPut(endpoint, data) {
    return apiCall(endpoint, {
        method: 'PUT',
        body: JSON.stringify(data)
    });
}

// DELETE 요청
async function apiDelete(endpoint, data) {
    const options = {
        method: 'DELETE'
    };

    // data가 있으면 body에 추가
    if (data) {
        options.body = JSON.stringify(data);
    }

    return apiCall(endpoint, options);
}

// 쿼리 파라미터 생성 헬퍼
function buildQueryParams(params) {
    const queryParams = new URLSearchParams();

    Object.keys(params).forEach(key => {
        if (params[key] !== null && params[key] !== undefined && params[key] !== '') {
            queryParams.append(key, params[key]);
        }
    });

    const queryString = queryParams.toString();
    return queryString ? `?${queryString}` : '';
}
