import axiosInstance from './axiosConfig';

/**
 * 회원가입
 * @param {Object} data - 회원가입 데이터
 * @param {string} data.nickname - 닉네임
 * @param {string} data.email - 이메일
 * @param {string} data.password - 비밀번호
 * @param {string} data.favoriteTeam - 응원팀 (TEAM enum)
 * @returns {Promise} 회원가입 결과
 */
export const signup = async (data) => {
  const response = await axiosInstance.post('/users', data);
  return response.data;
};

/**
 * 로그인
 * @param {Object} credentials - 로그인 정보
 * @param {string} credentials.email - 이메일
 * @param {string} credentials.password - 비밀번호
 * @returns {Promise} 로그인 결과 (토큰 포함)
 */
export const login = async (credentials) => {
  const response = await axiosInstance.post('/users/login', credentials);
  return response.data;
};

/**
 * 회원 탈퇴
 * @param {Object} data - 탈퇴 데이터
 * @param {string} data.password - 비밀번호 확인
 * @returns {Promise} 탈퇴 결과
 */
export const deleteAccount = async (data) => {
  const response = await axiosInstance.delete('/users', { data });
  return response.data;
};

/**
 * 내 프로필 조회
 * @returns {Promise} 프로필 정보
 */
export const getMyProfile = async () => {
  const response = await axiosInstance.get('/users/my-page');
  return response.data;
};

/**
 * 닉네임 변경
 * @param {Object} data - 닉네임 데이터
 * @param {string} data.nickname - 새 닉네임
 * @returns {Promise} 변경 결과
 */
export const updateNickname = async (data) => {
  const response = await axiosInstance.put('/users/nickname', data);
  return response.data;
};

/**
 * 비밀번호 변경
 * @param {Object} data - 비밀번호 데이터
 * @param {string} data.currentPassword - 현재 비밀번호
 * @param {string} data.newPassword - 새 비밀번호
 * @returns {Promise} 변경 결과
 */
export const updatePassword = async (data) => {
  const response = await axiosInstance.put('/users/password', data);
  return response.data;
};
