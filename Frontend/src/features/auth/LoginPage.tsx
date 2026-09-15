import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Globe, Eye, EyeOff, Loader2 } from 'lucide-react';
import { useAuthStore } from '../../store/authStore';
import type { UserRole } from '../../core/types/auth.types';
import './LoginPage.css';

// ── Demo users para hackathon (sin backend listo) ──────────────────────────
const DEMO_USERS = [
  { email: 'admin@impacto.co',     password: '123456', name: 'Ana García',     role: 'ADMIN'     as UserRole },
  { email: 'voluntario@impacto.co',password: '123456', name: 'Carlos Pérez',   role: 'VOLUNTEER' as UserRole },
  { email: 'donante@impacto.co',   password: '123456', name: 'María López',    role: 'DONOR'     as UserRole },
];

const ROLE_REDIRECT: Record<UserRole, string> = {
  ADMIN:     '/admin/dashboard',
  VOLUNTEER: '/volunteer/tasks',
  DONOR:     '/donor/home',
};

const LoginPage = () => {
  const navigate = useNavigate();
  const { setAuth } = useAuthStore();

  const [email, setEmail]         = useState('');
  const [password, setPassword]   = useState('');
  const [showPass, setShowPass]   = useState(false);
  const [loading, setLoading]     = useState(false);
  const [error, setError]         = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    await new Promise((r) => setTimeout(r, 800)); // simulación de red

    const found = DEMO_USERS.find(
      (u) => u.email === email && u.password === password,
    );

    if (!found) {
      setError('Correo o contraseña incorrectos');
      setLoading(false);
      return;
    }

    setAuth({ id: 1, name: found.name, email: found.email, role: found.role }, 'demo-token');
    navigate(ROLE_REDIRECT[found.role]);
  };

  const fillDemo = (role: UserRole) => {
    const user = DEMO_USERS.find((u) => u.role === role)!;
    setEmail(user.email);
    setPassword(user.password);
    setError('');
  };

  return (
    <div className="login-page">
      {/* Background decorative orbs */}
      <div className="login-orb login-orb--1" />
      <div className="login-orb login-orb--2" />

      <div className="login-card animate-in">
        {/* Header */}
        <div className="login-header">
          <div className="login-logo">
            <Globe size={24} />
          </div>
          <h1 className="login-title">Impacto</h1>
          <p className="login-subtitle">Sistema de Gestión de Ayudas Sociales</p>
        </div>

        {/* Form */}
        <form className="login-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label" htmlFor="email">Correo electrónico</label>
            <input
              id="email"
              className={`form-input ${error ? 'error' : ''}`}
              type="email"
              placeholder="correo@ejemplo.com"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              autoComplete="email"
            />
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="password">Contraseña</label>
            <div className="password-wrapper">
              <input
                id="password"
                className={`form-input ${error ? 'error' : ''}`}
                type={showPass ? 'text' : 'password'}
                placeholder="••••••••"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                autoComplete="current-password"
              />
              <button
                type="button"
                className="show-pass-btn"
                onClick={() => setShowPass(!showPass)}
                tabIndex={-1}
              >
                {showPass ? <EyeOff size={16} /> : <Eye size={16} />}
              </button>
            </div>
          </div>

          {error && <p className="form-error">{error}</p>}

          <button
            id="btn-login"
            type="submit"
            className="btn btn-primary login-submit"
            disabled={loading}
          >
            {loading
              ? <><Loader2 size={16} className="spin" /> Ingresando...</>
              : 'Ingresar'}
          </button>
        </form>

        {/* Demo shortcuts */}
        <div className="demo-section">
          <p className="demo-label">Acceso rápido demo</p>
          <div className="demo-buttons">
            <button className="demo-btn" onClick={() => fillDemo('ADMIN')}>
              👨‍💼 Admin
            </button>
            <button className="demo-btn" onClick={() => fillDemo('VOLUNTEER')}>
              🤝 Voluntario
            </button>
            <button className="demo-btn" onClick={() => fillDemo('DONOR')}>
              💰 Donante
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
