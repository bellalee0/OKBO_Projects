import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { Button, Input, Select } from '../../components/common';
import { TEAM_OPTIONS } from '../../utils/constants';
import { signup } from '../../api/authApi';

const SignupPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    nickname: '',
    email: '',
    password: '',
    passwordConfirm: '',
    favoriteTeam: '',
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

    if (!formData.nickname.trim()) {
      newErrors.nickname = '닉네임을 입력해주세요';
    }

    if (!formData.email.trim()) {
      newErrors.email = '이메일을 입력해주세요';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = '올바른 이메일 형식이 아닙니다';
    }

    if (!formData.password) {
      newErrors.password = '비밀번호를 입력해주세요';
    } else if (formData.password.length < 4) {
      newErrors.password = '비밀번호는 최소 4자 이상이어야 합니다';
    }

    if (!formData.passwordConfirm) {
      newErrors.passwordConfirm = '비밀번호 확인을 입력해주세요';
    } else if (formData.password !== formData.passwordConfirm) {
      newErrors.passwordConfirm = '비밀번호가 일치하지 않습니다';
    }

    if (!formData.favoriteTeam) {
      newErrors.favoriteTeam = '응원팀을 선택해주세요';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // 회원가입 제출
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    setIsLoading(true);

    try {
      const { passwordConfirm, ...signupData } = formData;
      await signup(signupData);

      alert('회원가입이 완료되었습니다! 로그인해주세요.');
      navigate('/login');
    } catch (error) {
      console.error('회원가입 실패:', error);

      if (error.status === 400) {
        setErrors({ email: '이미 사용 중인 이메일입니다' });
      } else {
        alert(error.message || '회원가입에 실패했습니다');
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
            회원가입
          </h2>
          <p className="text-sm text-gray-500 mt-2">
            KBO 팬 커뮤니티에 오신 것을 환영합니다
          </p>
        </div>

        {/* 회원가입 폼 */}
        <div className="bg-white rounded-lg shadow-lg p-8">
          <form onSubmit={handleSubmit} className="space-y-4">
            <Input
              label="닉네임"
              name="nickname"
              value={formData.nickname}
              onChange={handleChange}
              error={errors.nickname}
              placeholder="닉네임을 입력하세요"
              required
              fullWidth
            />

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

            <Input
              label="비밀번호 확인"
              type="password"
              name="passwordConfirm"
              value={formData.passwordConfirm}
              onChange={handleChange}
              error={errors.passwordConfirm}
              placeholder="비밀번호를 다시 입력하세요"
              required
              fullWidth
            />

            <Select
              label="응원팀"
              name="favoriteTeam"
              value={formData.favoriteTeam}
              onChange={handleChange}
              options={TEAM_OPTIONS}
              error={errors.favoriteTeam}
              placeholder="응원하는 팀을 선택하세요"
              required
              fullWidth
            />

            <Button
              type="submit"
              variant="primary"
              fullWidth
              disabled={isLoading}
            >
              {isLoading ? '회원가입 중...' : '회원가입'}
            </Button>
          </form>

          {/* 로그인 링크 */}
          <div className="mt-6 text-center">
            <p className="text-sm text-gray-600">
              이미 계정이 있으신가요?{' '}
              <Link
                to="/login"
                className="text-blue-600 hover:text-blue-700 font-medium"
              >
                로그인
              </Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SignupPage;
