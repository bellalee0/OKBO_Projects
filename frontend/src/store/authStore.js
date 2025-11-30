import { create } from 'zustand';
import { persist } from 'zustand/middleware';
import { STORAGE_KEYS } from '../utils/constants';

const useAuthStore = create(
  persist(
    (set) => ({
      user: null,
      isAuthenticated: false,
      
      login: (userData, token) => {
        localStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, token);
        set({
          user: userData,
          isAuthenticated: true
        });
      },

      logout: () => {
        localStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN);
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
      name: STORAGE_KEYS.AUTH_STORAGE,
      partialize: (state) => ({
        user: state.user,
        isAuthenticated: state.isAuthenticated
      }),
    }
  )
);

export default useAuthStore;
