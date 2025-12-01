import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Header, ProtectedRoute } from './components/common';
import useAuthStore from './store/authStore';

// Auth Pages
import SignupPage from './pages/auth/SignupPage';
import LoginPage from './pages/auth/LoginPage';
import SignoutPage from './pages/auth/SignoutPage';

// Profile Pages
import MyProfilePage from './pages/profile/MyProfilePage';
import EditProfilePage from './pages/profile/EditProfilePage';
import ChangePasswordPage from './pages/profile/ChangePasswordPage';

// Home Page (임시)
const HomePage = () => {
  const { isAuthenticated, user } = useAuthStore();

  return (
    <div className="max-w-4xl mx-auto p-8">
      <div className="bg-white rounded-lg shadow-lg p-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-4">
          ⚾ OKBO에 오신 것을 환영합니다!
        </h1>

        {isAuthenticated ? (
          <div className="space-y-4">
            <p className="text-lg text-gray-700">
              안녕하세요, <strong>{user?.nickname}</strong>님! 👋
            </p>
            <p className="text-gray-600">
              응원팀: <strong>{user?.team}</strong>
            </p>
            <div className="mt-6 p-4 bg-blue-50 rounded-lg">
              <h3 className="font-semibold text-blue-900 mb-2">
                ✅ Phase 1.3 완료!
              </h3>
              <p className="text-sm text-blue-700">
                인증 시스템이 성공적으로 구현되었습니다.
              </p>
              <ul className="mt-2 text-sm text-blue-700 space-y-1">
                <li>• 회원가입 ✓</li>
                <li>• 로그인 ✓</li>
                <li>• 로그아웃 ✓</li>
                <li>• 회원 탈퇴 ✓</li>
              </ul>
            </div>
          </div>
        ) : (
          <div className="space-y-4">
            <p className="text-lg text-gray-700">
              KBO 팬 커뮤니티 플랫폼입니다.
            </p>
            <p className="text-gray-600">
              로그인하여 커뮤니티에 참여하세요!
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

function App() {
  const { isAuthenticated } = useAuthStore();

  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Header />

        <Routes>
          {/* Home */}
          <Route path="/" element={<HomePage />} />

          {/* Auth Routes */}
          <Route
            path="/signup"
            element={
              isAuthenticated ? <Navigate to="/" replace /> : <SignupPage />
            }
          />
          <Route
            path="/login"
            element={
              isAuthenticated ? <Navigate to="/" replace /> : <LoginPage />
            }
          />
          <Route
            path="/signout"
            element={
              <ProtectedRoute>
                <SignoutPage />
              </ProtectedRoute>
            }
          />

          {/* Profile Routes */}
          <Route
            path="/profile"
            element={
              <ProtectedRoute>
                <MyProfilePage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/profile/edit"
            element={
              <ProtectedRoute>
                <EditProfilePage />
              </ProtectedRoute>
            }
          />
          <Route
            path="/profile/password"
            element={
              <ProtectedRoute>
                <ChangePasswordPage />
              </ProtectedRoute>
            }
          />

          {/* 404 - Not Found */}
          <Route
            path="*"
            element={
              <div className="max-w-4xl mx-auto p-8">
                <div className="bg-white rounded-lg shadow-lg p-8 text-center">
                  <h1 className="text-6xl font-bold text-gray-300 mb-4">404</h1>
                  <p className="text-xl text-gray-600">페이지를 찾을 수 없습니다</p>
                </div>
              </div>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
