import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import useAuthStore from './store/authStore';

function App() {
  const { isAuthenticated } = useAuthStore();

  return (
    <Router>
      <div className="min-h-screen bg-gray-50">
        <Routes>
          <Route 
            path="/" 
            element={
              <div className="flex items-center justify-center min-h-screen">
                <div className="text-center">
                  <h1 className="text-4xl font-bold text-gray-900 mb-4">
                    ⚾ OKBO
                  </h1>
                  <p className="text-xl text-gray-600">
                    KBO 팬 커뮤니티 플랫폼
                  </p>
                  <p className="text-sm text-gray-500 mt-4">
                    프론트엔드 개발 진행중...
                  </p>
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
