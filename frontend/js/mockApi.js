// Mock API Implementation
// 실제 백엔드 API를 시뮬레이션하는 가짜 API

// mockData.js를 먼저 로드해야 합니다

const MockAPI = {
  // ============ 사용자 관련 API ============

  // 회원가입
  async signup(data) {
    await createMockResponse(null, 500);

    // 이메일 중복 체크
    if (MOCK_USERS.find(u => u.email === data.email)) {
      throw new Error('이미 존재하는 이메일입니다.');
    }

    // 닉네임 중복 체크
    if (MOCK_USERS.find(u => u.nickname === data.nickname)) {
      throw new Error('이미 존재하는 닉네임입니다.');
    }

    const newUser = {
      id: nextUserId++,
      nickname: data.nickname,
      email: data.email,
      favoriteTeam: data.favoriteTeam,
      createdAt: createTimestamp()
    };

    MOCK_USERS.push(newUser);

    return {
      id: newUser.id,
      nickname: newUser.nickname,
      email: newUser.email,
      favoriteTeam: newUser.favoriteTeam
    };
  },

  // 로그인
  async login(data) {
    await createMockResponse(null, 500);

    const user = MOCK_USERS.find(u => u.email === data.email);
    if (!user) {
      throw new Error('이메일 또는 비밀번호가 올바르지 않습니다.');
    }

    // Mock JWT 토큰 생성
    const token = `mock-jwt-token-${user.id}-${Date.now()}`;
    CURRENT_USER = user;

    // localStorage에 사용자 정보 저장 (데모 모드 로그인 상태 유지)
    if (IS_DEMO_MODE) {
      localStorage.setItem('mock_user', JSON.stringify(user));
    }

    return token;
  },

  // 내 프로필 조회
  async getMyProfile() {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const followingCount = MOCK_FOLLOWS.filter(f => f.fromUserId === CURRENT_USER.id).length;
    const followerCount = MOCK_FOLLOWS.filter(f => f.toUserId === CURRENT_USER.id).length;

    // favoriteTeam을 한글 팀 이름으로 변환
    const teamNameMap = {
      'DOOSAN': '두산 베어즈',
      'HANWHA': '한화 이글스',
      'KIA': '기아 타이거즈',
      'KT': 'KT 위즈',
      'KIWOOM': '키움 히어로즈',
      'LG': 'LG 트윈스',
      'LOTTE': '롯데 자이언츠',
      'NC': 'NC 다이노스',
      'SAMSUNG': '삼성 라이온즈',
      'SSG': 'SSG 랜더스'
    };

    return {
      id: CURRENT_USER.id,
      nickname: CURRENT_USER.nickname,
      email: CURRENT_USER.email,
      favoriteTeam: CURRENT_USER.favoriteTeam,
      teamName: teamNameMap[CURRENT_USER.favoriteTeam] || CURRENT_USER.favoriteTeam,
      followingCount: followingCount,
      followerCount: followerCount
    };
  },

  // 다른 유저 조회
  async getUserProfile(nickname) {
    await createMockResponse(null, 300);

    const user = MOCK_USERS.find(u => u.nickname === nickname);
    if (!user) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }

    const followingCount = MOCK_FOLLOWS.filter(f => f.fromUserId === user.id).length;
    const followerCount = MOCK_FOLLOWS.filter(f => f.toUserId === user.id).length;
    const isFollowing = CURRENT_USER ?
        MOCK_FOLLOWS.some(f => f.fromUserId === CURRENT_USER.id && f.toUserId === user.id) : false;

    // favoriteTeam을 한글 팀 이름으로 변환
    const teamNameMap = {
      'DOOSAN': '두산 베어즈',
      'HANWHA': '한화 이글스',
      'KIA': '기아 타이거즈',
      'KT': 'KT 위즈',
      'KIWOOM': '키움 히어로즈',
      'LG': 'LG 트윈스',
      'LOTTE': '롯데 자이언츠',
      'NC': 'NC 다이노스',
      'SAMSUNG': '삼성 라이온즈',
      'SSG': 'SSG 랜더스'
    };

    return {
      id: user.id,
      nickname: user.nickname,
      favoriteTeam: user.favoriteTeam,
      teamName: teamNameMap[user.favoriteTeam] || user.favoriteTeam,
      followingCount: followingCount,
      followerCount: followerCount,
      isFollowing: isFollowing
    };
  },

  // 닉네임 변경
  async updateNickname(data) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    if (MOCK_USERS.find(u => u.nickname === data.nickname && u.id !== CURRENT_USER.id)) {
      throw new Error('이미 존재하는 닉네임입니다.');
    }

    CURRENT_USER.nickname = data.nickname;
    const user = MOCK_USERS.find(u => u.id === CURRENT_USER.id);
    if (user) user.nickname = data.nickname;

    return { nickname: data.nickname };
  },

  // ============ 게시글 관련 API ============

  // 게시글 전체 조회
  async getBoards(params = {}) {
    await createMockResponse(null, 400);

    let boards = MOCK_BOARDS.filter(b => !b.isDeleted);

    // 팀 필터
    if (params.team) {
      boards = boards.filter(b => b.team === params.team);
    }

    // 검색 필터
    if (params.title) {
      boards = boards.filter(b => b.title.includes(params.title));
    }

    if (params.writer) {
      boards = boards.filter(b => b.writer.includes(params.writer));
    }

    // 정렬 (최신순)
    boards.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

    return paginate(boards, params.page || 0, params.size || 10);
  },

  // 팔로잉 게시글 조회
  async getFollowingBoards(params = {}) {
    await createMockResponse(null, 400);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const followingUserIds = MOCK_FOLLOWS
    .filter(f => f.fromUserId === CURRENT_USER.id)
    .map(f => f.toUserId);

    let boards = MOCK_BOARDS.filter(b =>
        !b.isDeleted && followingUserIds.includes(b.writerId)
    );

    boards.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

    return paginate(boards, params.page || 0, params.size || 10);
  },

  // 내 게시글 조회
  async getMyBoards(params = {}) {
    await createMockResponse(null, 400);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    let boards = MOCK_BOARDS.filter(b =>
        !b.isDeleted && b.writerId === CURRENT_USER.id
    );

    boards.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

    return paginate(boards, params.page || 0, params.size || 10);
  },

  // 특정 유저의 게시글 조회
  async getUserBoards(nickname, params = {}) {
    await createMockResponse(null, 400);

    const user = MOCK_USERS.find(u => u.nickname === nickname);
    if (!user) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }

    let boards = MOCK_BOARDS.filter(b =>
        !b.isDeleted && b.writerId === user.id
    );

    boards.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));

    return paginate(boards, params.page || 0, params.size || 10);
  },

  // 게시글 상세 조회
  async getBoard(boardId) {
    await createMockResponse(null, 300);

    const board = MOCK_BOARDS.find(b => b.id === parseInt(boardId) && !b.isDeleted);
    if (!board) {
      throw new Error('게시글을 찾을 수 없습니다.');
    }

    const isLiked = CURRENT_USER ?
        MOCK_LIKES.boards.some(l => l.userId === CURRENT_USER.id && l.boardId === board.id) : false;

    return {
      ...board,
      isLiked: isLiked
    };
  },

  // 게시글 작성
  async createBoard(data) {
    await createMockResponse(null, 500);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const newBoard = {
      id: nextBoardId++,
      title: data.title,
      content: data.content,
      team: data.team,
      writer: CURRENT_USER.nickname,
      writerId: CURRENT_USER.id,
      likes: 0,
      comments: 0,
      createdAt: createTimestamp(),
      isDeleted: false
    };

    MOCK_BOARDS.unshift(newBoard);

    return {
      id: newBoard.id,
      title: newBoard.title,
      content: newBoard.content,
      team: newBoard.team,
      writer: newBoard.writer
    };
  },

  // 게시글 수정
  async updateBoard(boardId, data) {
    await createMockResponse(null, 500);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const board = MOCK_BOARDS.find(b => b.id === parseInt(boardId) && !b.isDeleted);
    if (!board) {
      throw new Error('게시글을 찾을 수 없습니다.');
    }

    if (board.writerId !== CURRENT_USER.id) {
      throw new Error('권한이 없습니다.');
    }

    board.title = data.title;
    board.content = data.content;

    return {
      id: board.id,
      title: board.title,
      content: board.content,
      team: board.team,
      writer: board.writer
    };
  },

  // 게시글 삭제
  async deleteBoard(boardId) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const board = MOCK_BOARDS.find(b => b.id === parseInt(boardId) && !b.isDeleted);
    if (!board) {
      throw new Error('게시글을 찾을 수 없습니다.');
    }

    if (board.writerId !== CURRENT_USER.id) {
      throw new Error('권한이 없습니다.');
    }

    board.isDeleted = true;

    // 관련 댓글도 삭제
    MOCK_COMMENTS.filter(c => c.boardId === board.id).forEach(c => c.isDeleted = true);
  },

  // 게시글 좋아요
  async toggleBoardLike(boardId) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const board = MOCK_BOARDS.find(b => b.id === parseInt(boardId) && !b.isDeleted);
    if (!board) {
      throw new Error('게시글을 찾을 수 없습니다.');
    }

    const likeIndex = MOCK_LIKES.boards.findIndex(
        l => l.userId === CURRENT_USER.id && l.boardId === board.id
    );

    if (likeIndex > -1) {
      // 좋아요 취소
      MOCK_LIKES.boards.splice(likeIndex, 1);
      board.likes = Math.max(0, board.likes - 1);
      return { liked: false };
    } else {
      // 좋아요 추가
      MOCK_LIKES.boards.push({ userId: CURRENT_USER.id, boardId: board.id });
      board.likes++;
      return { liked: true };
    }
  },

  // ============ 댓글 관련 API ============

  // 댓글 조회
  async getComments(boardId, params = {}) {
    await createMockResponse(null, 300);

    let comments = MOCK_COMMENTS.filter(c =>
        c.boardId === parseInt(boardId) && !c.isDeleted
    );

    comments.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));

    // 좋아요 상태 추가
    comments = comments.map(c => ({
      ...c,
      isLiked: CURRENT_USER ?
          MOCK_LIKES.comments.some(l => l.userId === CURRENT_USER.id && l.commentId === c.id) : false
    }));

    return paginate(comments, params.page || 0, params.size || 10);
  },

  // 댓글 작성
  async createComment(boardId, data) {
    await createMockResponse(null, 500);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const board = MOCK_BOARDS.find(b => b.id === parseInt(boardId) && !b.isDeleted);
    if (!board) {
      throw new Error('게시글을 찾을 수 없습니다.');
    }

    const newComment = {
      id: nextCommentId++,
      comments: data.comments,
      boardId: parseInt(boardId),
      writer: CURRENT_USER.nickname,
      writerId: CURRENT_USER.id,
      likes: 0,
      createdAt: createTimestamp(),
      isDeleted: false
    };

    MOCK_COMMENTS.push(newComment);
    board.comments++;

    return {
      id: newComment.id,
      comments: newComment.comments,
      writer: newComment.writer,
      createdAt: newComment.createdAt
    };
  },

  // 댓글 수정
  async updateComment(commentId, data) {
    await createMockResponse(null, 500);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const comment = MOCK_COMMENTS.find(c => c.id === parseInt(commentId) && !c.isDeleted);
    if (!comment) {
      throw new Error('댓글을 찾을 수 없습니다.');
    }

    if (comment.writerId !== CURRENT_USER.id) {
      throw new Error('권한이 없습니다.');
    }

    comment.comments = data.comments;

    return {
      id: comment.id,
      comments: comment.comments,
      writer: comment.writer
    };
  },

  // 댓글 삭제
  async deleteComment(commentId) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const comment = MOCK_COMMENTS.find(c => c.id === parseInt(commentId) && !c.isDeleted);
    if (!comment) {
      throw new Error('댓글을 찾을 수 없습니다.');
    }

    if (comment.writerId !== CURRENT_USER.id) {
      throw new Error('권한이 없습니다.');
    }

    comment.isDeleted = true;

    // 게시글의 댓글 수 감소
    const board = MOCK_BOARDS.find(b => b.id === comment.boardId);
    if (board) {
      board.comments = Math.max(0, board.comments - 1);
    }
  },

  // 댓글 좋아요
  async toggleCommentLike(commentId) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const comment = MOCK_COMMENTS.find(c => c.id === parseInt(commentId) && !c.isDeleted);
    if (!comment) {
      throw new Error('댓글을 찾을 수 없습니다.');
    }

    const likeIndex = MOCK_LIKES.comments.findIndex(
        l => l.userId === CURRENT_USER.id && l.commentId === comment.id
    );

    if (likeIndex > -1) {
      MOCK_LIKES.comments.splice(likeIndex, 1);
      comment.likes = Math.max(0, comment.likes - 1);
      return { liked: false };
    } else {
      MOCK_LIKES.comments.push({ userId: CURRENT_USER.id, commentId: comment.id });
      comment.likes++;
      return { liked: true };
    }
  },

  // ============ 팔로우 관련 API ============

  // 팔로우
  async follow(nickname) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const targetUser = MOCK_USERS.find(u => u.nickname === nickname);
    if (!targetUser) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }

    if (targetUser.id === CURRENT_USER.id) {
      throw new Error('자기 자신을 팔로우할 수 없습니다.');
    }

    const alreadyFollowing = MOCK_FOLLOWS.some(
        f => f.fromUserId === CURRENT_USER.id && f.toUserId === targetUser.id
    );

    if (alreadyFollowing) {
      throw new Error('이미 팔로우 중입니다.');
    }

    MOCK_FOLLOWS.push({
      fromUserId: CURRENT_USER.id,
      toUserId: targetUser.id
    });
  },

  // 언팔로우
  async unfollow(nickname) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const targetUser = MOCK_USERS.find(u => u.nickname === nickname);
    if (!targetUser) {
      throw new Error('사용자를 찾을 수 없습니다.');
    }

    const followIndex = MOCK_FOLLOWS.findIndex(
        f => f.fromUserId === CURRENT_USER.id && f.toUserId === targetUser.id
    );

    if (followIndex === -1) {
      throw new Error('팔로우하고 있지 않습니다.');
    }

    MOCK_FOLLOWS.splice(followIndex, 1);
  },

  // 팔로우 수 조회
  async getFollowCount(nickname) {
    await createMockResponse(null, 300);

    let userId;
    if (nickname) {
      const user = MOCK_USERS.find(u => u.nickname === nickname);
      if (!user) {
        throw new Error('사용자를 찾을 수 없습니다.');
      }
      userId = user.id;
    } else {
      if (!CURRENT_USER) {
        throw new Error('로그인이 필요합니다.');
      }
      userId = CURRENT_USER.id;
    }

    const followingCount = MOCK_FOLLOWS.filter(f => f.fromUserId === userId).length;
    const followerCount = MOCK_FOLLOWS.filter(f => f.toUserId === userId).length;

    return {
      following: followingCount,
      follower: followerCount
    };
  },

  // 팔로잉 목록
  async getFollowingList(params = {}) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const followingIds = MOCK_FOLLOWS
    .filter(f => f.fromUserId === CURRENT_USER.id)
    .map(f => f.toUserId);

    const followingUsers = MOCK_USERS
    .filter(u => followingIds.includes(u.id))
    .map(u => ({
      nickname: u.nickname,
      favoriteTeam: u.favoriteTeam
    }));

    return paginate(followingUsers, params.page || 0, params.size || 10);
  },

  // 팔로워 목록
  async getFollowerList(params = {}) {
    await createMockResponse(null, 300);

    if (!CURRENT_USER) {
      throw new Error('로그인이 필요합니다.');
    }

    const followerIds = MOCK_FOLLOWS
    .filter(f => f.toUserId === CURRENT_USER.id)
    .map(f => f.fromUserId);

    const followers = MOCK_USERS
    .filter(u => followerIds.includes(u.id))
    .map(u => ({
      nickname: u.nickname,
      favoriteTeam: u.favoriteTeam,
      isFollowing: MOCK_FOLLOWS.some(f =>
          f.fromUserId === CURRENT_USER.id && f.toUserId === u.id
      )
    }));

    return paginate(followers, params.page || 0, params.size || 10);
  }
};

console.log('🎯 Mock API Module Loaded');