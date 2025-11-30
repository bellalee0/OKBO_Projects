import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Button, Input } from '../../components/common';
import { login, getMyProfile } from '../../api/authApi';
import useAuthStore from '../../store/authStore';

const LoginPage = () => {
  const navigate = useNavigate();
  const { login: setAuthLogin } = useAuthStore();
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  // 입력값 변경 핸들러
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
    // 입력 시 해당 필드 에러 제거
    if (errors[name]) {
      setErrors((prev) => ({
        ...prev,
        [name]: '',
      }));
    }
  };

  // 유효성 검사
  const validate = () => {
    const newErrors = {};

    if (!formData.email.trim()) {
      newErrors.email = '이메일을 입력해주세요';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = '올바른 이메일 형식이 아닙니다';
    }

    if (!formData.password) {
      newErrors.password = '비밀번호를 입력해주세요';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // 로그인 제출
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    setIsLoading(true);

    try {
      // 1. 로그인 API 호출 - 토큰만 반환
      const token = await login(formData);

      // 2. 토큰을 localStorage에 임시 저장 (getMyProfile API가 인증 필요)
      localStorage.setItem('accessToken', token);

      // 3. 내 프로필 정보 조회
      const userData = await getMyProfile();

      // 4. Zustand 스토어에 저장
      setAuthLogin(userData, token);

      alert('로그인되었습니다!');
      navigate('/');
    } catch (error) {
      console.error('로그인 실패:', error);

      if (error.status === 401) {
        setErrors({ password: '이메일 또는 비밀번호가 올바르지 않습니다' });
      } else {
        alert(error.message || '로그인에 실패했습니다');
      }
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-md w-full">
        {/* 헤더 */}
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-gray-900 mb-2">
            ⚾ OKBO
          </h1>
          <h2 className="text-xl font-semibold text-gray-700">
            로그인
          </h2>
          <p className="text-sm text-gray-500 mt-2">
            KBO 팬 커뮤니티에 오신 것을 환영합니다
          </p>
        </div>

        {/* 로그인 폼 */}
        <div className="bg-white rounded-lg shadow-lg p-8">
          <form onSubmit={handleSubmit} className="space-y-4">
            <Input
              label="이메일"
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              error={errors.email}
              placeholder="이메일을 입력하세요"
              required
              fullWidth
            />

            <Input
              label="비밀번호"
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              error={errors.password}
              placeholder="비밀번호를 입력하세요"
              required
              fullWidth
            />

            <Button
              type="submit"
              variant="primary"
              fullWidth
              disabled={isLoading}
            >
              {isLoading ? '로그인 중...' : '로그인'}
            </Button>
          </form>

          {/* 회원가입 링크 */}
          <div className="mt-6 text-center">
            <p className="text-sm text-gray-600">
              아직 계정이 없으신가요?{' '}
              <Link
                to="/signup"
                className="text-blue-600 hover:text-blue-700 font-medium"
              >
                회원가입
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
