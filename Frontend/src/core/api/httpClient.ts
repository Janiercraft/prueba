import axios from 'axios';
import type { AxiosError, InternalAxiosRequestConfig } from 'axios';
import type { ApiError } from '../types/shared.types';

// ── Base URL desde variable de entorno ─────────────────────────────────────
const BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api/v1';

export const httpClient = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' },
  timeout: 15000,
});

// ── Interceptor de REQUEST: adjunta JWT en cada petición ───────────────────
httpClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem('impacto_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error),
);

// ── Interceptor de RESPONSE: manejo global de errores ──────────────────────
httpClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError<ApiError>) => {
    if (error.response?.status === 401) {
      // Token expirado → limpiar sesión y redirigir
      localStorage.removeItem('impacto_token');
      localStorage.removeItem('impacto_user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  },
);

export default httpClient;
