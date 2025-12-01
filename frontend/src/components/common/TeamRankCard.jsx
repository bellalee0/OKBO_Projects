import { getTeamRanking, getTeamColor } from '../../utils/teamRankings';

/**
 * 팀 순위 카드 컴포넌트
 * KBL 앱 스타일의 팀 정보 카드
 */
const TeamRankCard = ({ teamName }) => {
  const ranking = getTeamRanking(teamName);
  const teamColor = getTeamColor(teamName);

  if (!ranking) {
    return (
      <div className="bg-gray-100 rounded-lg p-6 text-center">
        <p className="text-gray-600">팀 정보를 찾을 수 없습니다.</p>
      </div>
    );
  }

  return (
    <div
      className="rounded-lg p-8 shadow-lg"
      style={{ backgroundColor: teamColor, color: '#FFFFFF' }}
    >
      {/* 팀 이름 */}
      <div className="text-center mb-6">
        <h2 className="text-3xl font-bold text-white">{teamName}</h2>
      </div>

      {/* 순위 및 전적 정보 */}
      <div className="grid grid-cols-4 gap-4 text-center text-white">
        {/* 순위 */}
        <div>
          <div className="text-sm opacity-90 mb-2">순위</div>
          <div className="text-4xl font-bold">{ranking.rank}</div>
        </div>

        {/* 승 */}
        <div>
          <div className="text-sm opacity-90 mb-2">승</div>
          <div className="text-4xl font-bold">{ranking.wins}</div>
        </div>

        {/* 패 */}
        <div>
          <div className="text-sm opacity-90 mb-2">패</div>
          <div className="text-4xl font-bold">{ranking.losses}</div>
        </div>

        {/* 무 */}
        <div>
          <div className="text-sm opacity-90 mb-2">무</div>
          <div className="text-4xl font-bold">{ranking.draws}</div>
        </div>
      </div>

      {/* 승률 (하단에 작게 표시) */}
      <div className="mt-6 text-center text-white">
        <span className="text-sm opacity-75">
          승률: {(ranking.winRate * 100).toFixed(1)}%
        </span>
      </div>
    </div>
  );
};

export default TeamRankCard;
