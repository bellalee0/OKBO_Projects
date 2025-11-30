import { Link } from 'react-router-dom';
import useAuthStore from '../../store/authStore';
import Button from './Button';

const Header = () => {
  const { isAuthenticated, user, logout } = useAuthStore();

  const handleLogout = () => {
    logout();
    window.location.href = '/';
  };

  return (
    <header className="bg-white shadow-md sticky top-0 z-40">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center gap-2">
            <span className="text-2xl">⚾</span>
            <span className="text-xl font-bold text-gray-900">OKBO</span>
          </Link>

          {/* Navigation */}
          <nav className="hidden md:flex items-center gap-6">
            <Link
              to="/boards"
              className="text-gray-700 hover:text-blue-600 transition-colors"
            >
              전체 게시판
            </Link>
            <Link
              to="/boards/team"
              className="text-gray-700 hover:text-blue-600 transition-colors"
            >
              팀별 게시판
            </Link>
            {isAuthenticated && (
              <Link
                to="/boards/my"
                className="text-gray-700 hover:text-blue-600 transition-colors"
              >
                내가 쓴 글
              </Link>
            )}
          </nav>

          {/* Auth Buttons */}
          <div className="flex items-center gap-3">
            {isAuthenticated ? (
              <>
                <Link to="/profile">
                  <Button variant="ghost" size="sm">
                    {user?.nickname || '프로필'}
                  </Button>
                </Link>
                <Button variant="outline" size="sm" onClick={handleLogout}>
                  로그아웃
                </Button>
              </>
            ) : (
              <>
                <Link to="/login">
                  <Button variant="ghost" size="sm">
                    로그인
                  </Button>
                </Link>
                <Link to="/signup">
                  <Button variant="primary" size="sm">
                    회원가입
                  </Button>
                </Link>
              </>
            )}
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
