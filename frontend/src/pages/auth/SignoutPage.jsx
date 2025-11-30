import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button, Input, Modal } from '../../components/common';
import { deleteAccount } from '../../api/authApi';
import useAuthStore from '../../store/authStore';

const SignoutPage = () => {
  const navigate = useNavigate();
  const { logout } = useAuthStore();
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  // 비밀번호 변경 핸들러
  const handleChange = (e) => {
    setPassword(e.target.value);
    if (error) {
      setError('');
    }
  };

  // 탈퇴 확인 모달 열기
  const handleOpenModal = (e) => {
    e.preventDefault();

    if (!password) {
      setError('비밀번호를 입력해주세요');
      return;
    }

    setIsModalOpen(true);
  };

  // 회원 탈퇴 실행
  const handleDeleteAccount = async () => {
    setIsLoading(true);

    try {
      await deleteAccount({ password });

      alert('회원 탈퇴가 완료되었습니다');

      // 로그아웃 처리
      logout();

      // 메인 페이지로 이동
      navigate('/');
    } catch (error) {
      console.error('회원 탈퇴 실패:', error);

      setIsModalOpen(false);

      if (error.status === 401) {
        setError('비밀번호가 올바르지 않습니다');
      } else {
        alert(error.message || '회원 탈퇴에 실패했습니다');
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md mx-auto">
        {/* 헤더 */}
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            회원 탈퇴
          </h1>
          <p className="text-sm text-gray-500">
            정말 탈퇴하시겠습니까?
          </p>
        </div>

        {/* 경고 메시지 */}
        <div className="bg-red-50 border border-red-200 rounded-lg p-6 mb-6">
          <h3 className="text-lg font-semibold text-red-800 mb-2">
            ⚠️ 주의사항
          </h3>
          <ul className="text-sm text-red-700 space-y-1">
            <li>• 탈퇴 시 모든 게시글과 댓글이 삭제됩니다</li>
            <li>• 탈퇴한 계정은 복구할 수 없습니다</li>
            <li>• 같은 이메일로 재가입할 수 없습니다</li>
          </ul>
        </div>

        {/* 비밀번호 확인 폼 */}
        <div className="bg-white rounded-lg shadow-lg p-8">
          <form onSubmit={handleOpenModal} className="space-y-4">
            <Input
              label="비밀번호 확인"
              type="password"
              value={password}
              onChange={handleChange}
              error={error}
              placeholder="비밀번호를 입력하세요"
              helperText="본인 확인을 위해 비밀번호를 입력해주세요"
              required
              fullWidth
            />

            <div className="flex gap-3">
              <Button
                type="button"
                variant="secondary"
                fullWidth
                onClick={() => navigate(-1)}
              >
                취소
              </Button>
              <Button
                type="submit"
                variant="danger"
                fullWidth
                disabled={isLoading}
              >
                탈퇴하기
              </Button>
            </div>
          </form>
        </div>

        {/* 탈퇴 확인 모달 */}
        <Modal
          isOpen={isModalOpen}
          onClose={() => setIsModalOpen(false)}
          title="회원 탈퇴 확인"
          size="sm"
        >
          <div className="space-y-4">
            <p className="text-gray-700">
              정말로 탈퇴하시겠습니까?
            </p>
            <p className="text-sm text-gray-500">
              이 작업은 되돌릴 수 없습니다.
            </p>

            <div className="flex gap-3 justify-end">
              <Button
                variant="secondary"
                onClick={() => setIsModalOpen(false)}
                disabled={isLoading}
              >
                취소
              </Button>
              <Button
                variant="danger"
                onClick={handleDeleteAccount}
                disabled={isLoading}
              >
                {isLoading ? '탈퇴 중...' : '탈퇴'}
              </Button>
            </div>
          </div>
        </Modal>
      </div>
    </div>
  );
};

export default SignoutPage;
