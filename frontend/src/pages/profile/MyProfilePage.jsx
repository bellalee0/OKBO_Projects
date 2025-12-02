import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { getMyProfile } from '../../api/authApi';
import { getFollowCount } from '../../api/followApi';
import { getMyBoards } from '../../api/boardApi';
import { Loading, TeamRankCard } from '../../components/common';
import { getTeamDataAttribute } from '../../utils/teamRankings';

const MyProfilePage = () => {
  const navigate = useNavigate();
  const [page, setPage] = useState(0);
  const pageSize = 10;

  // 데이터 페칭
  const { data: profile, isLoading: profileLoading, error: profileError } = useQuery({
    queryKey: ['myProfile'],
    queryFn: getMyProfile,
  });

  const { data: followCount, isLoading: followLoading } = useQuery({
    queryKey: ['followCount'],
    queryFn: () => getFollowCount(),
  });

  const { data: myBoards, isLoading: boardsLoading } = useQuery({
    queryKey: ['myBoards', page],
    queryFn: () => getMyBoards({ page, size: pageSize }),
  });

  // 팀 data 속성
  const teamDataAttr = profile?.teamName ? getTeamDataAttribute(profile.teamName) : '';

  // 로딩 상태
  if (profileLoading || followLoading) {
    return <Loading />;
  }

  // 에러 처리
  if (profileError) {
    return (
      <div className="max-w-7xl mx-auto p-8">
        <div className="bg-red-50 text-red-600 p-4 rounded-lg">
          프로필을 불러오는 중 오류가 발생했습니다.
        </div>
      </div>
    );
  }

  // 페이지 변경 핸들러
  const handlePageChange = (newPage) => {
    setPage(newPage);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  // 게시글 클릭 핸들러
  const handleBoardClick = (boardId) => {
    navigate(`/boards/${boardId}`);
  };

  return (
    <div
      data-team={teamDataAttr}
      style={{
        maxWidth: 'var(--site-width)',
        margin: '40px auto',
        padding: '0 10px'
      }}
    >
      <h1 className="text-4xl font-bold mb-8" style={{ color: 'var(--text-main)' }}>
        마이페이지
      </h1>

      {/* Grid 레이아웃: 280px (좌측) + 1fr (우측) */}
      <div
        className="layout-container"
        style={{
          display: 'grid',
          gridTemplateColumns: '280px 1fr',
          gap: '24px'
        }}
      >
        {/* ========== 왼쪽 영역 (280px 고정) ========== */}
        <div className="space-y-6">
          {/* 프로필 정보 카드 */}
          <div
            className="info-card profile-card"
            style={{
              backgroundColor: 'var(--bg-surface)',
              borderRadius: 'var(--radius-box)',
              boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
              padding: '12px 24px 16px 24px',
            }}
          >
            {/* 닉네임 */}
            <div style={{ marginBottom: '15px' }}>
              <h2 className="text-2xl font-bold" style={{ color: 'var(--text-main)', margin: 0, marginBottom: '8px', marginTop: '6px' }}>
                {profile?.nickname}<span style={{ fontWeight: 'normal', fontSize: '0.8em', marginLeft: '3px' }}>님</span>
              </h2>
              <p className="text-sm" style={{ color: 'var(--text-sub)', margin: 0 }}>
                {profile?.email}
              </p>
            </div>

            {/* 팔로잉/팔로우 수 */}
            <div className="grid grid-cols-2 gap-4" style={{ borderTop: '1px solid var(--border-line)', paddingTop: '10px' }}>
              <div className="text-center">
                <div className="text-4xl font-bold" style={{ color: 'var(--team-main)', fontSize: '1.5rem' }}>
                  {followCount?.following || 0}
                </div>
                <div className="text-xs mt-1 uppercase tracking-wide" style={{ color: 'var(--text-sub)' }}>
                  팔로잉
                </div>
              </div>
              <div className="text-center">
                <div className="text-4xl font-bold" style={{ color: 'var(--team-main)', fontSize: '1.5rem' }}>
                  {followCount?.follower || 0}
                </div>
                <div className="text-xs mt-1 uppercase tracking-wide" style={{ color: 'var(--text-sub)' }}>
                  팔로워
                </div>
              </div>
            </div>
          </div>

          {/* 응원팀 순위 카드 */}
          <div>
            <h3 className="text-sm font-semibold mb-3 uppercase tracking-wide" style={{ color: 'var(--text-main)' }}>
              내 응원팀
            </h3>
            <TeamRankCard teamName={profile?.teamName} />
          </div>

          {/* 계정 관리 */}
          <h3 className="text-sm font-semibold mb-4 uppercase tracking-wide" style={{ color: 'var(--text-main)' }}>
            계정 관리
          </h3>
          <div
            className="info-card"
            style={{
              backgroundColor: 'var(--bg-surface)',
              borderRadius: 'var(--radius-box)',
              boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
              padding: '24px'
            }}
          >
            <div className="space-y-5">
              <button
                onClick={() => navigate('/profile/edit')}
                className="w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-colors duration-200"
                style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  color: 'var(--text-main)',
                  marginBottom: '10px'
                }}
                onMouseEnter={(e) => e.currentTarget.style.backgroundColor = '#F9FAFB'}
                onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
              >
                <svg xmlns="http://www.w3.org/2000/svg" className="h-1 w-1" viewBox="0 0 20 20" fill="currentColor" style={{ width: '25px', height: '25px' }}>
                  <path fillRule="evenodd" d="M11.49 3.17c-.38-1.56-2.6-1.56-2.98 0a1.532 1.532 0 01-2.286.948c-1.372-.836-2.942.734-2.106 2.106.54.886.061 2.042-.947 2.287-1.561.379-1.561 2.6 0 2.978a1.532 1.532 0 01.947 2.287c-.836 1.372.734 2.942 2.106 2.106a1.532 1.532 0 012.287.947c.379 1.561 2.6 1.561 2.978 0a1.533 1.533 0 012.287-.947c1.372.836 2.942-.734 2.106-2.106a1.533 1.533 0 01.947-2.287c1.561-.379 1.561-2.6 0-2.978a1.532 1.532 0 01-.947-2.287c.836-1.372-.734-2.942-2.106-2.106a1.532 1.532 0 01-2.287-.947zM10 13a3 3 0 100-6 3 3 0 000 6z" clipRule="evenodd" />
                </svg>
                <span className="text-sm font-medium" style={{ fontSize: '1rem', marginLeft: '3px' }}>닉네임 변경</span>
              </button>
              <button
                onClick={() => navigate('/profile/password')}
                className="w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-colors duration-200"
                style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  color: 'var(--text-main)',
                  marginBottom: '10px'
                }}
                onMouseEnter={(e) => e.currentTarget.style.backgroundColor = '#F9FAFB'}
                onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
              >
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" style={{ width: '25px', height: '25px' }}>
                  <path fillRule="evenodd" d="M5 9V7a5 5 0 0110 0v2a2 2 0 012 2v5a2 2 0 01-2 2H5a2 2 0 01-2-2v-5a2 2 0 012-2zm8-2v2H7V7a3 3 0 016 0z" clipRule="evenodd" />
                </svg>
                <span className="text-sm font-medium" style={{ fontSize: '1rem', marginLeft: '3px' }}>비밀번호 변경</span>
              </button>
              <button
                onClick={() => navigate('/signout')}
                className="w-full flex items-center gap-3 px-4 py-3 rounded-lg transition-colors duration-200"
                style={{
                  backgroundColor: 'transparent',
                  border: 'none',
                  color: 'var(--text-main)'
                }}
                onMouseEnter={(e) => e.currentTarget.style.backgroundColor = '#F9FAFB'}
                onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
              >
                <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" style={{ width: '25px', height: '25px' }}>
                  <path fillRule="evenodd" d="M3 3a1 1 0 00-1 1v12a1 1 0 102 0V4a1 1 0 00-1-1zm10.293 9.293a1 1 0 001.414 1.414l3-3a1 1 0 000-1.414l-3-3a1 1 0 10-1.414 1.414L14.586 9H7a1 1 0 100 2h7.586l-1.293 1.293z" clipRule="evenodd" />
                </svg>
                <span className="text-sm font-medium" style={{ fontSize: '1rem', marginLeft: '3px' }}>회원 탈퇴</span>
              </button>
            </div>
          </div>
        </div>

        {/* ========== 오른쪽 영역 (1fr) ========== */}
        <div
          className="main-content"
          style={{
            backgroundColor: 'var(--bg-surface)',
            borderRadius: 'var(--radius-box)',
            boxShadow: '0 2px 8px rgba(0,0,0,0.05)',
            padding: '40px',
            minHeight: '600px'
          }}
        >
          {/* 헤더 */}
          <div
            className="content-header"
            style={{
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'flex-end',
              paddingBottom: '16px',
              borderBottom: '3px solid var(--team-main)'
            }}
          >
            <h2 className="text-2xl font-bold" style={{ color: 'var(--text-main)' }}>
              내가 쓴 글
            </h2>
            <span
              className="count-badge"
              style={{
                backgroundColor: 'var(--team-main)',
                color: '#fff',
                padding: '4px 12px',
                borderRadius: '12px',
                fontSize: '13px',
                fontWeight: 'bold'
              }}
            >
              총 {myBoards?.totalElements || 0}개
            </span>
          </div>

          {/* 게시글 목록 */}
          <div className="mt-6">
            {boardsLoading ? (
              <div className="text-center py-12">
                <Loading />
              </div>
            ) : myBoards?.content?.length === 0 ? (
              <div className="text-center py-16">
                <p className="text-lg" style={{ color: 'var(--text-sub)' }}>
                  작성한 게시글이 없습니다.
                </p>
              </div>
            ) : (
              <>
                {/* 테이블 */}
                <div className="overflow-x-auto">
                  <table className="w-full">
                    <thead style={{ backgroundColor: '#F9FAFB' }}>
                      <tr>
                        <th className="px-6 py-4 text-left text-xs font-semibold uppercase tracking-wider" style={{ color: 'var(--text-main)' }}>
                          제목
                        </th>
                        <th className="px-6 py-4 text-center text-xs font-semibold uppercase tracking-wider w-28" style={{ color: 'var(--text-main)' }}>
                          팀
                        </th>
                        <th className="px-6 py-4 text-center text-xs font-semibold uppercase tracking-wider w-20" style={{ color: 'var(--text-main)' }}>
                          댓글
                        </th>
                        <th className="px-6 py-4 text-center text-xs font-semibold uppercase tracking-wider w-20" style={{ color: 'var(--text-main)' }}>
                          좋아요
                        </th>
                        <th className="px-6 py-4 text-center text-xs font-semibold uppercase tracking-wider w-32" style={{ color: 'var(--text-main)' }}>
                          작성일
                        </th>
                      </tr>
                    </thead>
                    <tbody style={{ borderTop: '1px solid var(--border-line)' }}>
                      {myBoards?.content?.map((board) => (
                        <tr
                          key={board.id}
                          onClick={() => handleBoardClick(board.id)}
                          className="cursor-pointer transition-all duration-200"
                          style={{ borderBottom: '1px solid var(--border-line)' }}
                          onMouseEnter={(e) => e.currentTarget.style.backgroundColor = '#F9FAFB'}
                          onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
                        >
                          <td className="px-6 py-4">
                            <div className="font-medium" style={{ color: 'var(--text-main)' }}>
                              {board.title}
                            </div>
                          </td>
                          <td className="px-6 py-4 text-center">
                            <span
                              className="inline-flex items-center px-3 py-1 rounded-full text-xs font-medium"
                              style={{
                                backgroundColor: 'rgba(59, 130, 246, 0.1)',
                                color: '#2563eb'
                              }}
                            >
                              {board.team}
                            </span>
                          </td>
                          <td className="px-6 py-4 text-center font-medium" style={{ color: 'var(--text-sub)' }}>
                            {board.comments}
                          </td>
                          <td className="px-6 py-4 text-center font-medium" style={{ color: 'var(--text-sub)' }}>
                            {board.likes}
                          </td>
                          <td className="px-6 py-4 text-center text-sm" style={{ color: 'var(--text-sub)' }}>
                            {new Date(board.createdAt).toLocaleDateString('ko-KR', {
                              year: 'numeric',
                              month: 'long',
                              day: 'numeric'
                            })}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>

                {/* 페이지네이션 */}
                {myBoards?.totalPages > 1 && (
                  <div className="mt-8 flex justify-center items-center gap-2">
                    <button
                      onClick={() => handlePageChange(page - 1)}
                      disabled={page === 0}
                      className="px-4 py-2 rounded-lg transition-colors duration-200"
                      style={{
                        border: '1px solid var(--border-line)',
                        color: 'var(--text-main)',
                        opacity: page === 0 ? 0.5 : 1,
                        cursor: page === 0 ? 'not-allowed' : 'pointer'
                      }}
                      onMouseEnter={(e) => !e.currentTarget.disabled && (e.currentTarget.style.backgroundColor = '#F9FAFB')}
                      onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
                    >
                      이전
                    </button>

                    {Array.from({ length: myBoards.totalPages }, (_, i) => (
                      <button
                        key={i}
                        onClick={() => handlePageChange(i)}
                        className="px-4 py-2 rounded-lg font-medium transition-all duration-200"
                        style={{
                          backgroundColor: page === i ? 'var(--team-main)' : 'transparent',
                          border: page === i ? 'none' : '1px solid var(--border-line)',
                          color: page === i ? '#FFFFFF' : 'var(--text-main)',
                          boxShadow: page === i ? '0 2px 4px rgba(0,0,0,0.1)' : 'none'
                        }}
                        onMouseEnter={(e) => {
                          if (page !== i) e.currentTarget.style.backgroundColor = '#F9FAFB';
                        }}
                        onMouseLeave={(e) => {
                          if (page !== i) e.currentTarget.style.backgroundColor = 'transparent';
                        }}
                      >
                        {i + 1}
                      </button>
                    ))}

                    <button
                      onClick={() => handlePageChange(page + 1)}
                      disabled={page === myBoards.totalPages - 1}
                      className="px-4 py-2 rounded-lg transition-colors duration-200"
                      style={{
                        border: '1px solid var(--border-line)',
                        color: 'var(--text-main)',
                        opacity: page === myBoards.totalPages - 1 ? 0.5 : 1,
                        cursor: page === myBoards.totalPages - 1 ? 'not-allowed' : 'pointer'
                      }}
                      onMouseEnter={(e) => !e.currentTarget.disabled && (e.currentTarget.style.backgroundColor = '#F9FAFB')}
                      onMouseLeave={(e) => e.currentTarget.style.backgroundColor = 'transparent'}
                    >
                      다음
                    </button>
                  </div>
                )}
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyProfilePage;
