import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';
import {
  LayoutDashboard, Users, Baby, Heart, HandCoins,
  Package, Megaphone, UserCheck, Globe, LogOut, ChevronRight,
} from 'lucide-react';
import '../styles/AdminLayout.css';

const NAV_ITEMS = [
  { to: '/admin/dashboard',  label: 'Dashboard',   icon: LayoutDashboard },
  { to: '/admin/families',   label: 'Familias',    icon: Users },
  { to: '/admin/children',   label: 'Niños',       icon: Baby },
  { to: '/admin/needs',      label: 'Necesidades', icon: Heart },
  { to: '/admin/donations',  label: 'Donaciones',  icon: HandCoins },
  { to: '/admin/inventory',  label: 'Inventario',  icon: Package },
  { to: '/admin/campaigns',  label: 'Campañas',    icon: Megaphone },
  { to: '/admin/volunteers', label: 'Voluntarios', icon: UserCheck },
  { to: '/admin/impact',     label: 'Impacto',     icon: Globe },
];

const AdminLayout = () => {
  const { user, clearAuth } = useAuthStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    clearAuth();
    navigate('/login');
  };

  return (
    <div className="admin-layout">
      {/* ── Sidebar ──────────────────────────────────────────────────────── */}
      <aside className="sidebar">
        {/* Logo */}
        <div className="sidebar-logo">
          <div className="logo-icon">
            <Globe size={20} />
          </div>
          <span className="logo-text">Impacto</span>
        </div>

        {/* Nav */}
        <nav className="sidebar-nav">
          <p className="nav-section-label">Menú Principal</p>
          {NAV_ITEMS.map(({ to, label, icon: Icon }) => (
            <NavLink key={to} to={to} className={({ isActive }) =>
              `nav-item ${isActive ? 'nav-item--active' : ''}`
            }>
              <Icon size={18} className="nav-icon" />
              <span>{label}</span>
              <ChevronRight size={14} className="nav-chevron" />
            </NavLink>
          ))}
        </nav>

        {/* User */}
        <div className="sidebar-user">
          <div className="user-avatar">
            {user?.name?.charAt(0).toUpperCase()}
          </div>
          <div className="user-info">
            <span className="user-name">{user?.name}</span>
            <span className="user-role">Administrador</span>
          </div>
          <button className="logout-btn" onClick={handleLogout} title="Cerrar sesión">
            <LogOut size={16} />
          </button>
        </div>
      </aside>

      {/* ── Main ─────────────────────────────────────────────────────────── */}
      <main className="main-content">
        <Outlet />
      </main>
    </div>
  );
};

export default AdminLayout;
