export const TEAMS = {
  DOOSAN: { name: 'DOOSAN', displayName: '두산 베어즈', color: '#131230' },
  HANHWA: { name: 'HANHWA', displayName: '한화 이글스', color: '#FF6600' },
  KIA: { name: 'KIA', displayName: '기아 타이거즈', color: '#EA0029' },
  KT: { name: 'KT', displayName: 'KT 위즈', color: '#000000' },
  KIWOOM: { name: 'KIWOOM', displayName: '키움 히어로즈', color: '#570514' },
  LG: { name: 'LG', displayName: 'LG 트윈스', color: '#C30452' },
  LOTTE: { name: 'LOTTE', displayName: '롯데 자이언츠', color: '#041E42' },
  NC: { name: 'NC', displayName: 'NC 다이노스', color: '#315288' },
  SAMSUNG: { name: 'SAMSUNG', displayName: '삼성 라이온즈', color: '#074CA1' },
  SSG: { name: 'SSG', displayName: 'SSG 랜더스', color: '#CE0E2D' },
};

export const TEAM_LIST = Object.values(TEAMS);

export const getTeamByName = (teamName) => {
  return TEAMS[teamName] || null;
};

export const TEAM_OPTIONS = TEAM_LIST.map(team => ({
  value: team.name,
  label: team.displayName,
}));

// 페이지네이션 설정
export const PAGINATION = {
  DEFAULT_PAGE: 0,
  DEFAULT_SIZE: 10,
  DEFAULT_SORT: 'createdAt,desc',
};

// HTTP 상태 코드
export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  INTERNAL_SERVER_ERROR: 500,
};

// 로컬 스토리지 키
export const STORAGE_KEYS = {
  ACCESS_TOKEN: 'accessToken',
  AUTH_STORAGE: 'auth-storage',
};
