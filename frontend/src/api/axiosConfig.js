import axios from 'axios';
import { STORAGE_KEYS, HTTP_STATUS } from '../utils/constants';

// 개발 환경에서는 Vite proxy 사용 (baseURL을 빈 문자열로 설정)
// 프로덕션 빌드 시에는 환경 변수에서 실제 API URL 사용
const API_BASE_URL = import.meta.env.MODE === 'production'
  ? (import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080')
  : '';  // 개발 모드에서는 무조건 빈 문자열 (Vite proxy 사용)

console.log('🔧 Axios baseURL:', API_BASE_URL);
console.log('🔧 Mode:', import.meta.env.MODE);

const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 요청 인터셉터: JWT 토큰 자동 추가
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// 응답 인터셉터: 에러 처리
axiosInstance.interceptors.response.use(
  (response) => response,
  (error) => {
    // 네트워크 에러
    if (!error.response) {
      console.error('Network Error:', error);
      return Promise.reject({
        message: '네트워크 연결을 확인해주세요.',
        status: null,
      });
    }

    const { status, data } = error.response;

    // 백엔드 ErrorResponse 형식: { status, message }
    const errorMessage = data?.message || '알 수 없는 오류가 발생했습니다.';

    // 401 Unauthorized: 인증 실패
    if (status === HTTP_STATUS.UNAUTHORIZED) {
      localStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN);

      // 로그인 페이지가 아닌 경우에만 리다이렉트
      if (window.location.pathname !== '/login') {
        window.location.href = '/login';
      }
    }

    // 403 Forbidden: 권한 없음
    if (status === HTTP_STATUS.FORBIDDEN) {
      console.error('Access Forbidden:', errorMessage);
    }

    // 404 Not Found: 리소스 없음
    if (status === HTTP_STATUS.NOT_FOUND) {
      console.error('Resource Not Found:', errorMessage);
    }

    // 500 Internal Server Error: 서버 오류
    if (status >= HTTP_STATUS.INTERNAL_SERVER_ERROR) {
      console.error('Server Error:', errorMessage);
    }

    // 에러 객체를 일관된 형식으로 반환
    return Promise.reject({
      status,
      message: errorMessage,
      originalError: error,
    });
  }
);

export default axiosInstance;
