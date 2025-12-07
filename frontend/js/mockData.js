// Mock Data for OKBO Frontend Demo
// 실제 백엔드 없이 프론트엔드만 배포할 때 사용하는 가짜 데이터

// 환경 감지 (배포 환경인지 로컬 개발 환경인지)
const IS_DEMO_MODE = !window.location.hostname.includes('localhost') &&
    !window.location.hostname.includes('127.0.0.1');

// Mock 사용자 데이터
const MOCK_USERS = [
  {
    id: 1,
    nickname: "두산팬",
    email: "doosan@test.com",
    favoriteTeam: "DOOSAN",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 2,
    nickname: "한화팬",
    email: "hanwha@test.com",
    favoriteTeam: "HANWHA",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 3,
    nickname: "기아팬",
    email: "kia@test.com",
    favoriteTeam: "KIA",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 4,
    nickname: "크트팬",
    email: "kt@test.com",
    favoriteTeam: "KT",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 5,
    nickname: "키움팬",
    email: "kiwoom@test.com",
    favoriteTeam: "KIWOOM",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 6,
    nickname: "LG팬",
    email: "lg@test.com",
    favoriteTeam: "LG",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 7,
    nickname: "롯데팬",
    email: "lotte@test.com",
    favoriteTeam: "LOTTE",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 8,
    nickname: "NC팬",
    email: "nc@test.com",
    favoriteTeam: "NC",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 9,
    nickname: "삼성팬",
    email: "samsung@test.com",
    favoriteTeam: "SAMSUNG",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  },
  {
    id: 10,
    nickname: "쓱팬",
    email: "ssg@test.com",
    favoriteTeam: "SSG",
    createdAt: "2025-12-07T10:00:00",
    modifiedAt: "2025-12-07T10:00:00"
  }
];

// Mock 게시글 데이터
let MOCK_BOARDS = [
  {
    id: 1,
    title: "오늘 경기 정말 짜릿했습니다!",
    content: "9회말 역전 홈런으로 승리했네요. 정말 감동적인 경기였습니다. 우리 팀 파이팅!",
    team: "DOOSAN",
    writer: "야구팬123",
    writerId: 1,
    likes: 15,
    comments: 3,
    createdAt: "2024-11-25T20:30:00",
    isDeleted: false
  },
  {
    id: 2,
    title: "내일 경기 예측해봅니다",
    content: "내일 날씨도 좋고 우리 에이스가 선발이라 좋은 결과 있을 것 같아요!",
    team: "LG",
    writer: "LG사랑",
    writerId: 2,
    likes: 8,
    comments: 5,
    createdAt: "2024-11-26T15:20:00",
    isDeleted: false
  },
  {
    id: 3,
    title: "시즌 결산 - 우리 팀의 한 해",
    content: "올 시즌도 열심히 응원했습니다. 내년에는 더 좋은 성적 기대합니다!",
    team: "KIWOOM",
    writer: "키움홧팅",
    writerId: 3,
    likes: 22,
    comments: 8,
    createdAt: "2024-11-27T11:00:00",
    isDeleted: false
  },
  {
    id: 4,
    title: "신인 선수 활약이 인상적이네요",
    content: "이번에 올라온 신인 선수 정말 잘하는 것 같아요. 기대됩니다!",
    team: "DOOSAN",
    writer: "야구팬123",
    writerId: 1,
    likes: 12,
    comments: 2,
    createdAt: "2024-11-27T18:45:00",
    isDeleted: false
  },
  {
    id: 5,
    title: "직관 후기 공유합니다",
    content: "오늘 직관 다녀왔는데 분위기 정말 좋았어요. 현장에서 보니 더 재밌네요!",
    team: "LG",
    writer: "LG사랑",
    writerId: 2,
    likes: 18,
    comments: 6,
    createdAt: "2024-11-28T19:30:00",
    isDeleted: false
  }
];

// Mock 댓글 데이터
let MOCK_COMMENTS = [
  {
    id: 1,
    comments: "정말 감동적이었죠! 저도 그 순간 소름 돋았어요",
    boardId: 1,
    writer: "LG사랑",
    writerId: 2,
    likes: 3,
    createdAt: "2024-11-25T21:00:00",
    isDeleted: false
  },
  {
    id: 2,
    comments: "역전 홈런의 주인공이 진짜 멋있었습니다",
    boardId: 1,
    writerId: 3,
    writer: "키움홧팅",
    likes: 2,
    createdAt: "2024-11-25T21:15:00",
    isDeleted: false
  },
  {
    id: 3,
    comments: "내년 시즌도 기대됩니다!",
    boardId: 3,
    writer: "야구팬123",
    writerId: 1,
    likes: 1,
    createdAt: "2024-11-27T12:00:00",
    isDeleted: false
  }
];

// Mock 팔로우 데이터
let MOCK_FOLLOWS = [
  { fromUserId: 1, toUserId: 2 },
  { fromUserId: 1, toUserId: 3 },
  { fromUserId: 2, toUserId: 1 }
];

// Mock 좋아요 데이터
let MOCK_LIKES = {
  boards: [
    { userId: 1, boardId: 2 },
    { userId: 2, boardId: 1 },
    { userId: 3, boardId: 1 }
  ],
  comments: [
    { userId: 1, commentId: 2 }
  ]
};

// 현재 로그인한 사용자 (데모용)
let CURRENT_USER = null;

// ID 카운터 (새로운 데이터 생성 시 사용)
let nextBoardId = 6;
let nextCommentId = 4;
let nextUserId = 11;

// Mock API 응답 생성 헬퍼 함수
function createMockResponse(data, delay = 300) {
  return new Promise((resolve) => {
    setTimeout(() => resolve(data), delay);
  });
}

// 페이지네이션 헬퍼
function paginate(array, page = 0, size = 10) {
  const start = page * size;
  const end = start + size;
  const content = array.slice(start, end);

  return {
    content: content,
    totalElements: array.length,
    totalPages: Math.ceil(array.length / size),
    size: size,
    number: page,
    first: page === 0,
    last: page >= Math.ceil(array.length / size) - 1
  };
}

// 날짜 문자열 생성
function createTimestamp() {
  return new Date().toISOString();
}

console.log('🎭 Mock Data Module Loaded');
console.log('📊 Demo Mode:', IS_DEMO_MODE);
if (IS_DEMO_MODE) {
  console.log('⚠️ Running in DEMO mode - All data is temporary and stored in browser memory only');
}