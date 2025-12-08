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
    writer: "두산팬",
    writerId: 1,
    likes: 15,
    comments: 2,
    createdAt: "2025-12-06T20:30:00",
    modifiedAt: "2025-12-06T20:30:00",
    isDeleted: false
  },
  {
    id: 2,
    title: "내일 경기 예측해봅니다",
    content: "내일 날씨도 좋고 우리 에이스가 선발이라 좋은 결과 있을 것 같아요!",
    team: "LG",
    writer: "LG팬",
    writerId: 6,
    likes: 8,
    comments: 1,
    createdAt: "2025-12-05T15:20:00",
    modifiedAt: "2025-12-05T15:20:00",
    isDeleted: false
  },
  {
    id: 3,
    title: "시즌 결산 - 우리 팀의 한 해",
    content: "올 시즌도 열심히 응원했습니다. 내년에는 더 좋은 성적 기대합니다!",
    team: "KIWOOM",
    writer: "키움팬",
    writerId: 5,
    likes: 22,
    comments: 0,
    createdAt: "2025-12-04T11:00:00",
    modifiedAt: "2025-12-04T11:00:00",
    isDeleted: false
  },
  {
    id: 4,
    title: "한화 이글스 파이팅!",
    content: "우리 팀 올해는 꼭 좋은 성적 거두길 바랍니다. 팬들의 응원이 힘이 됩니다!",
    team: "HANWHA",
    writer: "한화팬",
    writerId: 2,
    likes: 12,
    comments: 0,
    createdAt: "2025-12-03T18:45:00",
    modifiedAt: "2025-12-03T18:45:00",
    isDeleted: false
  },
  {
    id: 5,
    title: "직관 후기 공유합니다",
    content: "오늘 직관 다녀왔는데 분위기 정말 좋았어요. 현장에서 보니 더 재밌네요!",
    team: "KIA",
    writer: "기아팬",
    writerId: 3,
    likes: 18,
    comments: 0,
    createdAt: "2025-12-02T19:30:00",
    modifiedAt: "2025-12-02T19:30:00",
    isDeleted: false
  },
  {
    id: 6,
    title: "KT 위즈 신인 선수 대박!",
    content: "이번 시즌 신인 선수들 활약이 정말 인상적이네요. 기대됩니다!",
    team: "KT",
    writer: "크트팬",
    writerId: 4,
    likes: 10,
    comments: 0,
    createdAt: "2025-12-01T14:20:00",
    modifiedAt: "2025-12-01T14:20:00",
    isDeleted: false
  },
  {
    id: 7,
    title: "롯데 자이언츠 응원합니다",
    content: "부산 팬으로서 우리 롯데 자이언츠를 항상 응원합니다!",
    team: "LOTTE",
    writer: "롯데팬",
    writerId: 7,
    likes: 14,
    comments: 0,
    createdAt: "2025-11-30T16:45:00",
    modifiedAt: "2025-11-30T16:45:00",
    isDeleted: false
  },
  {
    id: 8,
    title: "NC 다이노스 홈경기 후기",
    content: "창원에서 본 경기 정말 재밌었어요. 다음에 또 가고 싶네요!",
    team: "NC",
    writer: "NC팬",
    writerId: 8,
    likes: 9,
    comments: 0,
    createdAt: "2025-11-29T13:10:00",
    modifiedAt: "2025-11-29T13:10:00",
    isDeleted: false
  },
  {
    id: 9,
    title: "삼성 라이온즈 우승 가자!",
    content: "대구 시민으로서 삼성 라이온즈의 우승을 간절히 바랍니다!",
    team: "SAMSUNG",
    writer: "삼성팬",
    writerId: 9,
    likes: 20,
    comments: 0,
    createdAt: "2025-11-28T10:30:00",
    modifiedAt: "2025-11-28T10:30:00",
    isDeleted: false
  },
  {
    id: 10,
    title: "SSG 랜더스 신인왕 후보!",
    content: "우리 팀 신인 선수가 신인왕 후보에 올랐네요. 정말 자랑스럽습니다!",
    team: "SSG",
    writer: "쓱팬",
    writerId: 10,
    likes: 16,
    comments: 0,
    createdAt: "2025-11-27T09:15:00",
    modifiedAt: "2025-11-27T09:15:00",
    isDeleted: false
  }
];

// Mock 댓글 데이터
let MOCK_COMMENTS = [
  {
    id: 1,
    comments: "정말 감동적이었죠! 저도 그 순간 소름 돋았어요",
    boardId: 1,
    writer: "한화팬",
    writerId: 2,
    likes: 3,
    createdAt: "2025-12-06T21:00:00",
    modifiedAt: "2025-12-06T21:00:00",
    isDeleted: false
  },
  {
    id: 2,
    comments: "역전 홈런의 주인공이 진짜 멋있었습니다",
    boardId: 1,
    writerId: 5,
    writer: "키움팬",
    likes: 2,
    createdAt: "2025-12-06T21:15:00",
    modifiedAt: "2025-12-06T21:15:00",
    isDeleted: false
  },
  {
    id: 3,
    comments: "LG도 응원합니다!",
    boardId: 2,
    writer: "두산팬",
    writerId: 1,
    likes: 1,
    createdAt: "2025-12-05T16:00:00",
    modifiedAt: "2025-12-05T16:00:00",
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
let nextBoardId = 11;
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

// yyyyMMdd 형식의 문자열을 Date 객체로 변환
function parseDateString(dateStr) {
  if (!dateStr || dateStr.length !== 8) {
    return null;
  }
  const year = parseInt(dateStr.substring(0, 4));
  const month = parseInt(dateStr.substring(4, 6)) - 1; // 월은 0부터 시작
  const day = parseInt(dateStr.substring(6, 8));
  return new Date(year, month, day);
}

// 데모 모드에서 로그인 상태 및 회원가입 데이터 복원
if (IS_DEMO_MODE) {
  // 회원가입한 사용자 목록 복원
  const savedUsers = localStorage.getItem('mock_registered_users');
  if (savedUsers) {
    try {
      const registeredUsers = JSON.parse(savedUsers);
      // 기존 MOCK_USERS에 없는 사용자만 추가
      registeredUsers.forEach(user => {
        if (!MOCK_USERS.find(u => u.email === user.email)) {
          MOCK_USERS.push(user);
          // nextUserId 업데이트 (ID 중복 방지)
          if (user.id >= nextUserId) {
            nextUserId = user.id + 1;
          }
        }
      });
      console.log('✅ Restored registered users:', registeredUsers.length);
    } catch (e) {
      console.error('Failed to restore registered users:', e);
      localStorage.removeItem('mock_registered_users');
    }
  }

  // 로그인 상태 복원
  const savedUser = localStorage.getItem('mock_user');
  if (savedUser) {
    try {
      const user = JSON.parse(savedUser);
      // MOCK_USERS에서 해당 사용자 찾기 (이메일로 검색)
      const foundUser = MOCK_USERS.find(u => u.email === user.email);
      if (foundUser) {
        CURRENT_USER = foundUser;
        console.log('✅ Restored login state:', CURRENT_USER.nickname);
      } else {
        localStorage.removeItem('mock_user');
      }
    } catch (e) {
      console.error('Failed to restore user:', e);
      localStorage.removeItem('mock_user');
    }
  }
}

console.log('🎭 Mock Data Module Loaded');
console.log('📊 Demo Mode:', IS_DEMO_MODE);
if (IS_DEMO_MODE) {
  console.log('⚠️ Running in DEMO mode - All data is temporary and stored in browser memory only');
}