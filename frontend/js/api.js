// API Base URL
const API_BASE_URL = 'http://localhost:8080';

// 환경 감지 (배포 환경인지 확인)
const IS_DEMO_MODE = !window.location.hostname.includes('localhost') &&
    !window.location.hostname.includes('127.0.0.1');

console.log('🌐 Current hostname:', window.location.hostname);
console.log('🎭 Demo Mode:', IS_DEMO_MODE);

if (IS_DEMO_MODE) {
  console.log('⚠️ Running in DEMO mode');
  console.log('📝 All changes are temporary and stored in browser memory only');
  console.log('🔄 Refresh the page to reset all data');
}

// API 공통 호출 함수 (실제 백엔드용)
async function apiCallReal(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;

  const headers = {
    'Content-Type': 'application/json',
    ...options.headers
  };

  // JWT 토큰이 있으면 헤더에 추가
  const token = localStorage.getItem('token');
  if (token && token !== 'undefined' && typeof token === 'string') {
    headers['Authorization'] = `Bearer ${token}`;
  }

  try {
    const response = await fetch(url, {
      ...options,
      headers
    });

    // 인증 실패 시 처리
    if (response.status === 401) {
      if (endpoint === '/users/login') {
        throw new Error('이메일 또는 비밀번호가 올바르지 않습니다.');
      }
      alert('로그인이 필요합니다.');
      localStorage.removeItem('token');
      window.location.href = '/frontend/index.html';
      return;
    }

    if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || '요청에 실패했습니다.');
    }

    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
      return await response.json();
    } else {
      return await response.text();
    }
  } catch (error) {
    console.error('API 호출 에러:', error);
    throw error;
  }
}

// Mock API 호출 매핑 함수
async function apiCallMock(endpoint, options = {}) {
  const method = options.method || 'GET';
  const body = options.body ? JSON.parse(options.body) : null;

  try {
    // URL 파라미터 파싱
    const [path, queryString] = endpoint.split('?');
    const params = {};
    if (queryString) {
      queryString.split('&').forEach(param => {
        const [key, value] = param.split('=');
        params[decodeURIComponent(key)] = decodeURIComponent(value);
      });
    }

    // 경로 파싱 (동적 ID 추출)
    const pathParts = path.split('/').filter(p => p);

    // ========== 사용자 API ==========
    if (path === '/users' && method === 'POST') {
      return await MockAPI.signup(body);
    }
    if (path === '/users/login' && method === 'POST') {
      return await MockAPI.login(body);
    }
    if (path === '/users/my-page' && method === 'GET') {
      return await MockAPI.getMyProfile();
    }
    if (pathParts[0] === 'users' && pathParts.length === 2 && method === 'GET') {
      return await MockAPI.getUserProfile(pathParts[1]);
    }
    if (path === '/users/nickname' && method === 'PUT') {
      return await MockAPI.updateNickname(body);
    }

    // ========== 게시글 API ==========
    if (path.startsWith('/boards/all') && method === 'GET') {
      return await MockAPI.getBoards(params);
    }
    if (path.startsWith('/boards/follow') && method === 'GET') {
      return await MockAPI.getFollowingBoards(params);
    }
    if (path.startsWith('/boards/team') && method === 'GET') {
      return await MockAPI.getBoards({ ...params, team: params.team });
    }
    if (path === '/boards/my-boards' && method === 'GET') {
      return await MockAPI.getMyBoards(params);
    }
    if (pathParts[0] === 'boards' && pathParts[1] === 'user' && pathParts.length === 3 && method === 'GET') {
      return await MockAPI.getUserBoards(pathParts[2], params);
    }
    if (pathParts[0] === 'boards' && pathParts[1] === 'board' && pathParts.length === 3 && method === 'GET') {
      return await MockAPI.getBoard(pathParts[2]);
    }
    if (path === '/boards' && method === 'POST') {
      return await MockAPI.createBoard(body);
    }
    if (pathParts[0] === 'boards' && pathParts.length === 2 && method === 'PUT') {
      return await MockAPI.updateBoard(pathParts[1], body);
    }
    if (pathParts[0] === 'boards' && pathParts.length === 2 && method === 'DELETE') {
      await MockAPI.deleteBoard(pathParts[1]);
      return '';
    }
    if (pathParts[0] === 'likes' && pathParts[1] === 'boards' && pathParts.length === 3 && method === 'POST') {
      return await MockAPI.toggleBoardLike(pathParts[2]);
    }
    if (pathParts[0] === 'likes' && pathParts[1] === 'boards' && pathParts.length === 3 && method === 'DELETE') {
      return await MockAPI.toggleBoardLike(pathParts[2]);
    }

    // ========== 댓글 API ==========
    if (pathParts[0] === 'comments' && pathParts[1] === 'boards' && pathParts.length === 3 && method === 'GET') {
      return await MockAPI.getComments(pathParts[2], params);
    }
    if (pathParts[0] === 'comments' && pathParts[1] === 'boards' && pathParts.length === 3 && method === 'POST') {
      return await MockAPI.createComment(pathParts[2], body);
    }
    if (pathParts[0] === 'comments' && pathParts.length === 2 && method === 'PUT') {
      return await MockAPI.updateComment(pathParts[1], body);
    }
    if (pathParts[0] === 'comments' && pathParts.length === 2 && method === 'DELETE') {
      await MockAPI.deleteComment(pathParts[1]);
      return '';
    }
    if (pathParts[0] === 'likes' && pathParts[1] === 'comments' && pathParts.length === 3 && method === 'POST') {
      return await MockAPI.toggleCommentLike(pathParts[2]);
    }
    if (pathParts[0] === 'likes' && pathParts[1] === 'comments' && pathParts.length === 3 && method === 'DELETE') {
      return await MockAPI.toggleCommentLike(pathParts[2]);
    }

    // ========== 팔로우 API ==========
    if (pathParts[0] === 'follows' && pathParts.length === 2 && method === 'POST') {
      await MockAPI.follow(pathParts[1]);
      return '';
    }
    if (pathParts[0] === 'follows' && pathParts.length === 2 && method === 'DELETE') {
      await MockAPI.unfollow(pathParts[1]);
      return '';
    }
    if (path === '/follows/follow-count' && method === 'GET') {
      return await MockAPI.getFollowCount(params.userNickname);
    }
    if (path === '/follows/following' && method === 'GET') {
      return await MockAPI.getFollowingList(params);
    }
    if (path === '/follows/follower' && method === 'GET') {
      return await MockAPI.getFollowerList(params);
    }

    console.warn('⚠️ Unhandled mock API call:', method, endpoint);
    throw new Error('Mock API endpoint not implemented: ' + endpoint);

  } catch (error) {
    console.error('Mock API 에러:', error);
    throw error;
  }
}

// API 호출 함수 (환경에 따라 분기)
async function apiCall(endpoint, options = {}) {
  if (IS_DEMO_MODE) {
    return await apiCallMock(endpoint, options);
  } else {
    return await apiCallReal(endpoint, options);
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

// 데모 모드 안내 표시
if (IS_DEMO_MODE) {
  window.addEventListener('DOMContentLoaded', () => {
    const banner = document.createElement('div');
    banner.style.cssText = `
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 10px;
            text-align: center;
            font-size: 14px;
            z-index: 10000;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        `;
    banner.innerHTML = `
            <strong>🎭 데모 모드</strong> | 
            모든 데이터는 임시로 저장되며, 페이지를 새로고침하면 초기화됩니다. 
            <button onclick="this.parentElement.remove()" style="margin-left: 10px; padding: 2px 8px; border: 1px solid white; background: transparent; color: white; cursor: pointer; border-radius: 3px;">닫기</button>
        `;
    document.body.prepend(banner);

    // 헤더가 있으면 위치 조정
    const header = document.querySelector('header');
    if (header) {
      header.style.marginTop = '40px';
    }
  });
}