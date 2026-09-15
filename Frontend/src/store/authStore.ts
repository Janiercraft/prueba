import { create } from 'zustand';
import type { AuthUser, UserRole } from '../core/types/auth.types';

interface AuthStore {
  user: AuthUser | null;
  token: string | null;
  isAuthenticated: boolean;

  setAuth: (user: AuthUser, token: string) => void;
  clearAuth: () => void;
  hasRole: (role: UserRole) => boolean;
}

// Inicializar desde localStorage si existe sesión previa
const storedToken = localStorage.getItem('impacto_token');
const storedUser = localStorage.getItem('impacto_user');

export const useAuthStore = create<AuthStore>((set, get) => ({
  user: storedUser ? JSON.parse(storedUser) : null,
  token: storedToken,
  isAuthenticated: !!storedToken,

  setAuth: (user, token) => {
    localStorage.setItem('impacto_token', token);
    localStorage.setItem('impacto_user', JSON.stringify(user));
    set({ user, token, isAuthenticated: true });
  },

  clearAuth: () => {
    localStorage.removeItem('impacto_token');
    localStorage.removeItem('impacto_user');
    set({ user: null, token: null, isAuthenticated: false });
  },

  hasRole: (role) => get().user?.role === role,
}));
