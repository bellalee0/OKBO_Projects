import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { getMyProfile } from '../../api/authApi';
import { getFollowCount } from '../../api/followApi';
import { getMyBoards } from '../../api/boardApi';
import { Loading, TeamRankCard } from '../../components/common';

const MyProfilePage = () => {
  const navigate = useNavigate();
  const [page, setPage] = useState(0);
  const pageSize = 10;

  // 1단계: 데이터 페칭 설정
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
    <div className="max-w-7xl mx-auto p-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">마이페이지</h1>

      {/* 2단 레이아웃 */}
      <div className="flex gap-8">

        {/* ========== 왼쪽 영역 (프로필) ========== */}
        <div className="w-1/3 space-y-6 flex-shrink-0">

          {/* 2단계: 왼쪽 상단 - 사용자 기본 정보 */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <div className="space-y-3">
              {/* 닉네임 */}
              <div>
                <h2 className="text-2xl font-bold text-gray-900">
                  {profile?.nickname}님
                </h2>
                <p className="text-gray-600 text-sm mt-1">
                  ({profile?.email})
                </p>
              </div>

              {/* 팔로잉/팔로우 수 */}
              <div className="space-y-2 pt-3">
                <div className="flex justify-between items-center">
                  <span className="text-gray-700">팔로잉</span>
                  <span className="font-semibold text-gray-900">
                    {followCount?.following || 0}
                  </span>
                </div>
                <div className="flex justify-between items-center">
                  <span className="text-gray-700">팔로우</span>
                  <span className="font-semibold text-gray-900">
                    {followCount?.follower || 0}
                  </span>
                </div>
              </div>
            </div>
          </div>

          {/* 2단계: 왼쪽 중단 - 응원팀 순위 카드 */}
          <div>
            <h3 className="text-lg font-semibold text-gray-900 mb-3">
              내 응원팀
            </h3>
            <TeamRankCard teamName={profile?.teamName} />
          </div>

          {/* 2단계: 왼쪽 하단 - 액션 버튼 */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">
              계정 관리
            </h3>
            <div className="space-y-3">
              <button
                onClick={() => navigate('/profile/edit')}
                className="w-full px-4 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors font-medium"
              >
                닉네임 변경
              </button>
              <button
                onClick={() => navigate('/profile/password')}
                className="w-full px-4 py-3 bg-gray-600 text-white rounded-lg hover:bg-gray-700 transition-colors font-medium"
              >
                비밀번호 변경
              </button>
              <button
                onClick={() => navigate('/signout')}
                className="w-full px-4 py-3 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors font-medium"
              >
                회원 탈퇴
              </button>
            </div>
          </div>
        </div>

        {/* ========== 오른쪽 영역 (내가 쓴 글) ========== */}
        <div className="flex-1">
          <div className="bg-white rounded-lg shadow-lg p-6">

            {/* 3단계: 오른쪽 상단 - 헤더 */}
            <div className="flex justify-between items-center mb-6">
              <h2 className="text-2xl font-bold text-gray-900">내가 쓴 글</h2>
              <span className="text-sm text-gray-500">
                총 {myBoards?.totalElements || 0}개
              </span>
            </div>

            {/* 3단계: 게시글 목록 */}
            {boardsLoading ? (
              <div className="text-center py-8">
                <Loading />
              </div>
            ) : myBoards?.content?.length === 0 ? (
              <div className="text-center py-12">
                <p className="text-gray-500">작성한 게시글이 없습니다.</p>
              </div>
            ) : (
              <>
                {/* 테이블 형식 게시글 목록 */}
                <div className="overflow-x-auto">
                  <table className="w-full">
                    <thead className="bg-gray-50 border-b border-gray-200">
                      <tr>
                        <th className="px-4 py-3 text-left text-sm font-semibold text-gray-700">
                          제목
                        </th>
                        <th className="px-4 py-3 text-center text-sm font-semibold text-gray-700 w-24">
                          팀
                        </th>
                        <th className="px-4 py-3 text-center text-sm font-semibold text-gray-700 w-20">
                          댓글
                        </th>
                        <th className="px-4 py-3 text-center text-sm font-semibold text-gray-700 w-20">
                          좋아요
                        </th>
                        <th className="px-4 py-3 text-center text-sm font-semibold text-gray-700 w-32">
                          작성일
                        </th>
                      </tr>
                    </thead>
                    <tbody className="divide-y divide-gray-200">
                      {myBoards?.content?.map((board) => (
                        <tr
                          key={board.id}
                          onClick={() => handleBoardClick(board.id)}
                          className="hover:bg-gray-50 cursor-pointer transition-colors"
                        >
                          <td className="px-4 py-4">
                            <div className="text-gray-900 font-medium line-clamp-1">
                              {board.title}
                            </div>
                          </td>
                          <td className="px-4 py-4 text-center">
                            <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
                              {board.team}
                            </span>
                          </td>
                          <td className="px-4 py-4 text-center text-gray-600">
                            {board.comments}
                          </td>
                          <td className="px-4 py-4 text-center text-gray-600">
                            {board.likes}
                          </td>
                          <td className="px-4 py-4 text-center text-sm text-gray-500">
                            {new Date(board.createdAt).toLocaleDateString('ko-KR')}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>

                {/* 3단계: 페이지네이션 */}
                {myBoards?.totalPages > 1 && (
                  <div className="mt-6 flex justify-center items-center gap-2">
                    {/* 이전 페이지 버튼 */}
                    <button
                      onClick={() => handlePageChange(page - 1)}
                      disabled={page === 0}
                      className="px-3 py-2 rounded-lg border border-gray-300 text-gray-700 hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
                    >
                      이전
                    </button>

                    {/* 페이지 번호 */}
                    {Array.from({ length: myBoards.totalPages }, (_, i) => (
                      <button
                        key={i}
                        onClick={() => handlePageChange(i)}
                        className={`px-4 py-2 rounded-lg ${
                          page === i
                            ? 'bg-blue-600 text-white'
                            : 'border border-gray-300 text-gray-700 hover:bg-gray-50'
                        }`}
                      >
                        {i + 1}
                      </button>
                    ))}

                    {/* 다음 페이지 버튼 */}
                    <button
                      onClick={() => handlePageChange(page + 1)}
                      disabled={page === myBoards.totalPages - 1}
                      className="px-3 py-2 rounded-lg border border-gray-300 text-gray-700 hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
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
