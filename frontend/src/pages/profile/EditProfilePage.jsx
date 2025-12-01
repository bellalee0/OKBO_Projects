import { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { updateNickname } from '../../api/authApi';
import useAuthStore from '../../store/authStore';
import { Button, Input } from '../../components/common';

const EditProfilePage = () => {
  const { user, updateUser } = useAuthStore();
  const [nickname, setNickname] = useState(user?.nickname || '');
  const [error, setError] = useState('');
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: updateNickname,
    onSuccess: (data) => {
      // TODO: Step 4에서 구현
      console.log('닉네임 변경 성공:', data);
    },
    onError: (error) => {
      setError(error.message || '닉네임 변경에 실패했습니다.');
    },
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    setError('');

    if (!nickname.trim()) {
      setError('닉네임을 입력해주세요.');
      return;
    }

    // TODO: Step 4에서 API 호출 구현
    console.log('닉네임 변경:', nickname);
  };

  return (
    <div className="max-w-2xl mx-auto p-8">
      <div className="bg-white rounded-lg shadow-lg p-8">
        <h1 className="text-2xl font-bold text-gray-900 mb-6">프로필 수정</h1>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              닉네임
            </label>
            <Input
              type="text"
              value={nickname}
              onChange={(e) => setNickname(e.target.value)}
              placeholder="새 닉네임을 입력하세요"
            />
          </div>

          {error && (
            <div className="bg-red-50 text-red-600 p-3 rounded-lg text-sm">
              {error}
            </div>
          )}

          <div className="flex gap-4">
            <Button type="submit" disabled={mutation.isPending}>
              {mutation.isPending ? '저장 중...' : '저장'}
            </Button>
            <Button
              type="button"
              variant="secondary"
              onClick={() => window.history.back()}
            >
              취소
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditProfilePage;
