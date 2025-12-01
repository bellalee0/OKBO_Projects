import axiosInstance from './axiosConfig';

/**
 * 팔로워/팔로잉 수 조회
 * @param {string} userNickname - 조회할 사용자 닉네임 (optional, 없으면 본인)
 * @returns {Promise} 팔로우 카운트 정보 { following: number, follower: number }
 */
export const getFollowCount = async (userNickname = null) => {
  const params = userNickname ? { userNickname } : {};
  const response = await axiosInstance.get('/follows/follow-count', { params });
  return response.data;
};

/**
 * 팔로우 생성
 * @param {string} userNickname - 팔로우할 사용자 닉네임
 * @returns {Promise} 팔로우 생성 결과
 */
export const createFollow = async (userNickname) => {
  const response = await axiosInstance.post(`/follows/${userNickname}`);
  return response.data;
};

/**
 * 팔로우 취소
 * @param {string} userNickname - 팔로우 취소할 사용자 닉네임
 * @returns {Promise} 팔로우 취소 결과
 */
export const deleteFollow = async (userNickname) => {
  const response = await axiosInstance.delete(`/follows/${userNickname}`);
  return response.data;
};

/**
 * 팔로잉 목록 조회
 * @param {Object} params - 조회 파라미터
 * @param {number} params.page - 페이지 번호 (기본값: 0)
 * @param {number} params.size - 페이지 크기 (기본값: 10)
 * @param {string} params.userNickname - 조회할 사용자 닉네임 (optional)
 * @returns {Promise} 팔로잉 목록
 */
export const getFollowingList = async ({ page = 0, size = 10, userNickname = null } = {}) => {
  const params = { page, size };
  if (userNickname) params.userNickname = userNickname;

  const response = await axiosInstance.get('/follows/following', { params });
  return response.data;
};

/**
 * 팔로워 목록 조회
 * @param {Object} params - 조회 파라미터
 * @param {number} params.page - 페이지 번호 (기본값: 0)
 * @param {number} params.size - 페이지 크기 (기본값: 10)
 * @param {string} params.userNickname - 조회할 사용자 닉네임 (optional)
 * @returns {Promise} 팔로워 목록
 */
export const getFollowerList = async ({ page = 0, size = 10, userNickname = null } = {}) => {
  const params = { page, size };
  if (userNickname) params.userNickname = userNickname;

  const response = await axiosInstance.get('/follows/follower', { params });
  return response.data;
};
