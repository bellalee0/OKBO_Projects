import axiosInstance from './axiosConfig';

/**
 * 내가 작성한 게시글 목록 조회
 * @param {Object} params - 조회 파라미터
 * @param {string} params.title - 제목 검색어 (optional)
 * @param {string} params.startDate - 시작일 (yyyyMMdd) (optional)
 * @param {string} params.endDate - 종료일 (yyyyMMdd) (optional)
 * @param {number} params.page - 페이지 번호 (기본값: 0)
 * @param {number} params.size - 페이지 크기 (기본값: 10)
 * @returns {Promise} 내 게시글 목록 (Page 객체)
 */
export const getMyBoards = async ({
  title = null,
  startDate = null,
  endDate = null,
  page = 0,
  size = 10
} = {}) => {
  const params = { page, size };
  if (title) params.title = title;
  if (startDate) params.startDate = startDate;
  if (endDate) params.endDate = endDate;

  const response = await axiosInstance.get('/boards/my-board', { params });
  return response.data;
};

/**
 * 게시글 전체 목록 조회
 * @param {Object} params - 조회 파라미터
 * @param {string} params.title - 제목 검색어 (optional)
 * @param {string} params.writer - 작성자 검색어 (optional)
 * @param {string} params.startDate - 시작일 (yyyyMMdd) (optional)
 * @param {string} params.endDate - 종료일 (yyyyMMdd) (optional)
 * @param {number} params.page - 페이지 번호 (기본값: 0)
 * @param {number} params.size - 페이지 크기 (기본값: 10)
 * @returns {Promise} 게시글 목록 (Page 객체)
 */
export const getAllBoards = async ({
  title = null,
  writer = null,
  startDate = null,
  endDate = null,
  page = 0,
  size = 10
} = {}) => {
  const params = { page, size };
  if (title) params.title = title;
  if (writer) params.writer = writer;
  if (startDate) params.startDate = startDate;
  if (endDate) params.endDate = endDate;

  const response = await axiosInstance.get('/boards', { params });
  return response.data;
};

/**
 * 팀별 게시글 목록 조회
 * @param {string} teamName - 팀 이름
 * @param {Object} params - 조회 파라미터
 * @param {string} params.title - 제목 검색어 (optional)
 * @param {string} params.writer - 작성자 검색어 (optional)
 * @param {string} params.startDate - 시작일 (yyyyMMdd) (optional)
 * @param {string} params.endDate - 종료일 (yyyyMMdd) (optional)
 * @param {number} params.page - 페이지 번호 (기본값: 0)
 * @param {number} params.size - 페이지 크기 (기본값: 10)
 * @returns {Promise} 팀 게시글 목록 (Page 객체)
 */
export const getTeamBoards = async (teamName, {
  title = null,
  writer = null,
  startDate = null,
  endDate = null,
  page = 0,
  size = 10
} = {}) => {
  const params = { page, size };
  if (title) params.title = title;
  if (writer) params.writer = writer;
  if (startDate) params.startDate = startDate;
  if (endDate) params.endDate = endDate;

  const response = await axiosInstance.get(`/boards/teams/${teamName}`, { params });
  return response.data;
};

/**
 * 팔로우한 사용자의 게시글 목록 조회
 * @param {Object} params - 조회 파라미터
 * @param {string} params.title - 제목 검색어 (optional)
 * @param {string} params.writer - 작성자 검색어 (optional)
 * @param {string} params.startDate - 시작일 (yyyyMMdd) (optional)
 * @param {string} params.endDate - 종료일 (yyyyMMdd) (optional)
 * @param {number} params.page - 페이지 번호 (기본값: 0)
 * @param {number} params.size - 페이지 크기 (기본값: 10)
 * @returns {Promise} 팔로잉 게시글 목록 (Page 객체)
 */
export const getFollowingBoards = async ({
  title = null,
  writer = null,
  startDate = null,
  endDate = null,
  page = 0,
  size = 10
} = {}) => {
  const params = { page, size };
  if (title) params.title = title;
  if (writer) params.writer = writer;
  if (startDate) params.startDate = startDate;
  if (endDate) params.endDate = endDate;

  const response = await axiosInstance.get('/boards/followings', { params });
  return response.data;
};

/**
 * 게시글 상세 조회
 * @param {number} boardId - 게시글 ID
 * @param {Object} params - 조회 파라미터
 * @param {number} params.page - 댓글 페이지 번호 (기본값: 0)
 * @param {number} params.size - 댓글 페이지 크기 (기본값: 10)
 * @returns {Promise} 게시글 상세 정보 (댓글 포함)
 */
export const getBoardDetail = async (boardId, { page = 0, size = 10 } = {}) => {
  const params = { page, size };
  const response = await axiosInstance.get(`/boards/board/${boardId}`, { params });
  return response.data;
};

/**
 * 게시글 작성
 * @param {Object} data - 게시글 데이터
 * @param {string} data.title - 제목
 * @param {string} data.content - 내용
 * @param {string} data.team - 팀 이름
 * @returns {Promise} 생성된 게시글 정보
 */
export const createBoard = async (data) => {
  const response = await axiosInstance.post('/boards', data);
  return response.data;
};

/**
 * 게시글 수정
 * @param {number} boardId - 게시글 ID
 * @param {Object} data - 수정할 게시글 데이터
 * @param {string} data.title - 제목
 * @param {string} data.content - 내용
 * @param {string} data.team - 팀 이름
 * @returns {Promise} 수정된 게시글 정보
 */
export const updateBoard = async (boardId, data) => {
  const response = await axiosInstance.put(`/boards/${boardId}`, data);
  return response.data;
};

/**
 * 게시글 삭제
 * @param {number} boardId - 게시글 ID
 * @returns {Promise} 삭제 결과
 */
export const deleteBoard = async (boardId) => {
  const response = await axiosInstance.delete(`/boards/${boardId}`);
  return response.data;
};
