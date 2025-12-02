import { getTeamRanking, getTeamDataAttribute } from '../../utils/teamRankings';

/**
 * 팀 순위 카드 컴포넌트
 * StyleGuide 기반 디자인 (솔리드 컬러 + 도넛 차트)
 */
const TeamRankCard = ({ teamName }) => {
  const ranking = getTeamRanking(teamName);
  const teamDataAttr = getTeamDataAttribute(teamName);

  if (!ranking) {
    return (
      <div className="bg-gray-100 rounded-xl p-6 text-center border border-gray-200">
        <p className="text-gray-600">팀 정보를 찾을 수 없습니다.</p>
      </div>
    );
  }

  const winRatePercentage = (ranking.winRate * 100).toFixed(1);

  return (
    <div
      data-team={teamDataAttr}
      className="my-team-card"
      style={{
        backgroundColor: 'var(--team-main)',
        borderRadius: 'var(--radius-box)',
        padding: '10px 24px 16px 24px',
        color: '#FFFFFF',
        position: 'relative'
      }}
    >
      {/* 팀 이름 */}
      <div className="text-center mb-6" style={{ margin: '0px' }}>
        <h2 className="text-xl font-bold tracking-tight">{teamName}</h2>
      </div>

      {/* 승률 도넛 차트 */}
      <div className="flex justify-center mb-6">
        <div
          className="win-rate-donut"
          style={{
            width: '80px',
            height: '80px',
            borderRadius: '50%',
            background: `conic-gradient(
              #ffffff 0% ${winRatePercentage}%,
              rgba(255,255,255,0.3) ${winRatePercentage}% 100%
            )`,
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            position: 'relative'
          }}
        >
          {/* 도넛 차트 중앙 (구멍) */}
          <div
            style={{
              width: '68px',
              height: '68px',
              backgroundColor: 'var(--team-main)',
              borderRadius: '50%',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              fontWeight: 'normal',
              fontSize: '16px'
            }}
          >
            {winRatePercentage}%
          </div>
        </div>
      </div>

      {/* 순위 및 전적 정보 */}
      <div className="grid grid-cols-4 gap-4 text-center" style={{ paddingTop: '15px' }}>
        {/* 순위 */}
        <div>
          <div className="text-xs opacity-80 mb-2 uppercase tracking-wide font-medium">순위</div>
          <div className="text-4xl font-bold" style={{ fontSize: '1.5rem' }}>{ranking.rank}</div>
        </div>

        {/* 승 */}
        <div>
          <div className="text-xs opacity-80 mb-2 uppercase tracking-wide font-medium">승</div>
          <div className="text-4xl font-bold" style={{ fontSize: '1.5rem' }}>{ranking.wins}</div>
        </div>

        {/* 패 */}
        <div>
          <div className="text-xs opacity-80 mb-2 uppercase tracking-wide font-medium">패</div>
          <div className="text-4xl font-bold" style={{ fontSize: '1.5rem' }}>{ranking.losses}</div>
        </div>

        {/* 무 */}
        <div>
          <div className="text-xs opacity-80 mb-2 uppercase tracking-wide font-medium">무</div>
          <div className="text-4xl font-bold" style={{ fontSize: '1.5rem' }}>{ranking.draws}</div>
        </div>
      </div>
    </div>
  );
};

export default TeamRankCard;
