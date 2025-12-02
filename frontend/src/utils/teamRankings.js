/**
 * KBO 2025 시즌 팀 순위 데이터
 *
 * 업데이트 날짜: 2025.10.04
 * 출처: https://www.koreabaseball.com/Record/TeamRank/TeamRankDaily.aspx
 *
 * 주의: 이 데이터는 정적 데이터이므로 시즌 중 수동으로 업데이트해야 합니다.
 */

export const TEAM_RANKINGS_2025 = {
  'LG 트윈스': {
    rank: 1,
    wins: 85,
    losses: 56,
    draws: 3,
    winRate: 0.603
  },
  '한화 이글스': {
    rank: 2,
    wins: 83,
    losses: 57,
    draws: 4,
    winRate: 0.593
  },
  'SSG 랜더스': {
    rank: 3,
    wins: 75,
    losses: 65,
    draws: 4,
    winRate: 0.536
  },
  '삼성 라이온즈': {
    rank: 4,
    wins: 74,
    losses: 68,
    draws: 2,
    winRate: 0.521
  },
  'NC 다이노스': {
    rank: 5,
    wins: 71,
    losses: 67,
    draws: 6,
    winRate: 0.514
  },
  'KT 위즈': {
    rank: 6,
    wins: 71,
    losses: 68,
    draws: 5,
    winRate: 0.511
  },
  '롯데 자이언츠': {
    rank: 7,
    wins: 66,
    losses: 72,
    draws: 6,
    winRate: 0.478
  },
  '기아 타이거즈': {
    rank: 8,
    wins: 65,
    losses: 75,
    draws: 4,
    winRate: 0.464
  },
  '두산 베어즈': {
    rank: 9,
    wins: 61,
    losses: 77,
    draws: 6,
    winRate: 0.442
  },
  '키움 히어로즈': {
    rank: 10,
    wins: 47,
    losses: 93,
    draws: 4,
    winRate: 0.336
  }
};

/**
 * 팀 이름으로 순위 정보 조회
 * @param {string} teamName - 팀 이름 (예: 'LG', 'Hanhwa', 'SSG')
 * @returns {Object|null} 팀 순위 정보 또는 null
 */
export const getTeamRanking = (teamName) => {
  return TEAM_RANKINGS_2025[teamName] || null;
};

/**
 * 전체 팀 순위 목록 조회 (순위순으로 정렬)
 * @returns {Array} 팀 순위 배열
 */
export const getAllTeamRankings = () => {
  return Object.entries(TEAM_RANKINGS_2025)
    .map(([teamName, data]) => ({ teamName, ...data }))
    .sort((a, b) => a.rank - b.rank);
};

/**
 * 팀 색상 매핑 (팀 카드 배경색용)
 */
export const TEAM_COLORS = {
  'LG 트윈스': '#C30452',      // 트윈스 레드
  '한화 이글스': '#FF6600',     // 이글스 오렌지
  'SSG 랜더스': '#CE0E2D',      // 랜더스 레드
  '삼성 라이온즈': '#074CA1',   // 라이온즈 블루
  'NC 다이노스': '#1C498E',     // 다이노스 네이비
  'KT 위즈': '#000000',         // 위즈 블랙
  '롯데 자이언츠': '#041E42',   // 자이언츠 네이비
  '기아 타이거즈': '#EA0029',   // 타이거즈 레드
  '두산 베어즈': '#131230',     // 베어스 네이비
  '키움 히어로즈': '#570514'    // 히어로즈 버건디
};

/**
 * 팀 색상 조회
 * @param {string} teamName - 팀 이름
 * @returns {string} 팀 색상 (기본값: 그레이)
 */
export const getTeamColor = (teamName) => {
  return TEAM_COLORS[teamName] || '#6B7280';
};

/**
 * 팀 이름을 data-team 속성 값으로 변환
 * @param {string} teamName - 팀 이름 (예: 'LG 트윈스', 'KT 위즈')
 * @returns {string} data-team 속성 값 (예: 'lg', 'kt')
 */
export const getTeamDataAttribute = (teamName) => {
  const teamMap = {
    'KT 위즈': 'kt',
    'LG 트윈스': 'lg',
    '두산 베어스': 'doosan',
    'SSG 랜더스': 'ssg',
    '기아 타이거즈': 'kia',
    '롯데 자이언츠': 'lotte',
    '삼성 라이온즈': 'samsung',
    '한화 이글스': 'hanwha',
    'NC 다이노스': 'nc',
    '키움 히어로즈': 'kiwoom'
  };
  return teamMap[teamName] || '';
};
