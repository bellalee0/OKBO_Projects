import { create } from 'zustand';
import { persist } from 'zustand/middleware';

const useAuthStore = create(
  persist(
    (set) => ({
      user: null,
      isAuthenticated: false,
      
      login: (userData, token) => {
        localStorage.setItem('accessToken', token);
        set({ 
          user: userData, 
          isAuthenticated: true 
        });
      },
      
      logout: () => {
        localStorage.removeItem('accessToken');
        set({ 
          user: null, 
          isAuthenticated: false 
        });
      },
      
      updateUser: (userData) => {
        set({ user: userData });
      },
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({ 
        user: state.user,
        isAuthenticated: state.isAuthenticated 
      }),
    }
  )
);

export default useAuthStore;
