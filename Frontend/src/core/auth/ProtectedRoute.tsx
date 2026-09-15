import React from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import type { UserRole } from '../types/auth.types';
import { useAuthStore } from '../../store/authStore';

interface ProtectedRouteProps {
  allowedRoles: UserRole[];
}

/**
 * ProtectedRoute — Principio L (Liskov):
 * Funciona de forma intercambiable para cualquier combinación de roles.
 * Si el usuario no está autenticado → /login
 * Si el usuario no tiene el rol requerido → /unauthorized
 */
const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ allowedRoles }) => {
  const { isAuthenticated, user } = useAuthStore();

  if (!isAuthenticated) return <Navigate to="/login" replace />;

  if (user && !allowedRoles.includes(user.role)) {
    return <Navigate to="/unauthorized" replace />;
  }

  return <Outlet />;
};

export default ProtectedRoute;
