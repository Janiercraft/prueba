import type { AuthResponse, LoginCredentials } from '../types/auth.types';
import httpClient from './httpClient';

export const authApi = {
  login: (credentials: LoginCredentials) =>
    httpClient.post<AuthResponse>('/auth/login', credentials).then((r) => r.data),

  logout: () =>
    httpClient.post('/auth/logout').then((r) => r.data),
};
