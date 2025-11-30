import { Navigate } from 'react-router-dom';
import useAuthStore from '../../store/authStore';
import { STORAGE_KEYS } from '../../utils/constants';

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated } = useAuthStore();
  const token = localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN);

  if (!isAuthenticated || !token) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
