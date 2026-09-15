import {
  BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer,
  PieChart, Pie, Cell, Legend,
} from 'recharts';
import { Users, Baby, Package, Heart, HandCoins, Megaphone, UserCheck, TrendingUp } from 'lucide-react';
import './AdminDashboard.css';

// ── Mock Data ──────────────────────────────────────────────────────────────
const KPI_DATA = [
  { label: 'Familias',       value: 248,  icon: Users,      color: '#14B8A6', delta: '+12 este mes',  bg: 'rgba(20, 184, 166, 0.08)'  },
  { label: 'Niños',          value: 417,  icon: Baby,       color: '#3B82F6', delta: '+28 este mes',  bg: 'rgba(59, 130, 246, 0.08)'  },
  { label: 'Recursos Disp.', value: 183,  icon: Package,    color: '#F59E0B', delta: '43 asignados',  bg: 'rgba(245, 158, 11, 0.08)'  },
  { label: 'Atendidas',      value: 126,  icon: Heart,      color: '#22C55E', delta: '+18 este mes',  bg: 'rgba(34, 197, 94, 0.08)'   },
];

const SECONDARY_KPI = [
  { label: 'Donaciones',   value: 'COP 8.4M', icon: HandCoins,  color: '#A78BFA' },
  { label: 'Campañas',     value: 4,           icon: Megaphone,  color: '#F472B6' },
  { label: 'Voluntarios',  value: 31,          icon: UserCheck,  color: '#34D399' },
  { label: 'Pendientes',   value: 89,          icon: TrendingUp, color: '#FB923C' },
];

const MONTHLY_DATA = [
  { mes: 'Ene', entregas: 12, donaciones: 8 },
  { mes: 'Feb', entregas: 18, donaciones: 15 },
  { mes: 'Mar', entregas: 22, donaciones: 20 },
  { mes: 'Abr', entregas: 15, donaciones: 12 },
  { mes: 'May', entregas: 30, donaciones: 25 },
  { mes: 'Jun', entregas: 28, donaciones: 22 },
  { mes: 'Jul', entregas: 35, donaciones: 30 },
  { mes: 'Ago', entregas: 40, donaciones: 35 },
  { mes: 'Sep', entregas: 32, donaciones: 28 },
];

const NEEDS_BY_TYPE = [
  { name: 'Computador', value: 35, color: '#14B8A6' },
  { name: 'Kit Escolar', value: 28, color: '#3B82F6' },
  { name: 'Alimentos',   value: 20, color: '#F59E0B' },
  { name: 'Ropa',        value: 10, color: '#A78BFA' },
  { name: 'Tablet',      value: 7,  color: '#F472B6' },
];

const RECENT_ACTIVITY = [
  { icon: '📦', text: 'PC-0051 entregado a Familia Pérez',       time: 'Hace 5 min',  type: 'success' },
  { icon: '💰', text: 'Donación recibida de Carlos Martínez',    time: 'Hace 12 min', type: 'info'    },
  { icon: '👨‍👩‍👧', text: 'Nueva familia registrada en Bogotá',    time: 'Hace 1h',    type: 'neutral' },
  { icon: '❤️', text: 'Necesidad ATENDIDA: Kit escolar Familia López', time: 'Hace 2h', type: 'success' },
  { icon: '🎯', text: 'Campaña "Regreso a Clases" alcanzó su meta', time: 'Hace 3h',  type: 'warning' },
];

// ── Custom Tooltip ─────────────────────────────────────────────────────────
const CustomTooltip = ({ active, payload, label }: any) => {
  if (!active || !payload?.length) return null;
  return (
    <div className="chart-tooltip">
      <p className="chart-tooltip-label">{label}</p>
      {payload.map((p: any) => (
        <p key={p.name} style={{ color: p.color }}>
          {p.name}: <strong>{p.value}</strong>
        </p>
      ))}
    </div>
  );
};

const AdminDashboard = () => (
  <div className="dashboard">
    {/* ── Page Header ──────────────────────────────────────────────────── */}
    <div className="page-header animate-in">
      <div>
        <h1 className="page-title">Dashboard</h1>
        <p className="page-subtitle">Vista general del sistema — Septiembre 2026</p>
      </div>
    </div>

    {/* ── KPI Cards principales ─────────────────────────────────────────── */}
    <div className="grid-4 animate-in animate-delay-1">
      {KPI_DATA.map(({ label, value, icon: Icon, color, delta, bg }) => (
        <div key={label} className="kpi-card glass-card">
          <div className="kpi-icon-wrap" style={{ background: bg, border: `1px solid ${color}33` }}>
            <Icon size={20} style={{ color }} />
          </div>
          <div className="kpi-body">
            <span className="kpi-label">{label}</span>
            <span className="kpi-value" style={{ color }}>{value.toLocaleString()}</span>
            <span className="kpi-delta">{delta}</span>
          </div>
        </div>
      ))}
    </div>

    {/* ── KPI secundarios ─────────────────────────────────────────────────── */}
    <div className="grid-4 animate-in animate-delay-2" style={{ marginTop: 16 }}>
      {SECONDARY_KPI.map(({ label, value, icon: Icon, color }) => (
        <div key={label} className="kpi-mini glass-card">
          <Icon size={16} style={{ color }} />
          <span className="kpi-mini-value">{typeof value === 'number' ? value.toLocaleString() : value}</span>
          <span className="kpi-mini-label">{label}</span>
        </div>
      ))}
    </div>

    {/* ── Charts row ───────────────────────────────────────────────────────── */}
    <div className="dashboard-charts animate-in animate-delay-3">
      {/* Bar Chart */}
      <div className="chart-card glass-card">
        <h3 className="chart-title">Entregas y Donaciones por Mes</h3>
        <ResponsiveContainer width="100%" height={220}>
          <BarChart data={MONTHLY_DATA} barGap={4} barCategoryGap="30%">
            <XAxis dataKey="mes" tick={{ fill: '#64748B', fontSize: 12 }} axisLine={false} tickLine={false} />
            <YAxis tick={{ fill: '#64748B', fontSize: 12 }} axisLine={false} tickLine={false} />
            <Tooltip content={<CustomTooltip />} cursor={{ fill: 'rgba(255,255,255,0.03)' }} />
            <Bar dataKey="entregas"  name="Entregas"   fill="#14B8A6" radius={[4,4,0,0]} />
            <Bar dataKey="donaciones" name="Donaciones" fill="#3B82F6" radius={[4,4,0,0]} />
          </BarChart>
        </ResponsiveContainer>
      </div>

      {/* Pie Chart */}
      <div className="chart-card glass-card">
        <h3 className="chart-title">Necesidades por Tipo</h3>
        <ResponsiveContainer width="100%" height={220}>
          <PieChart>
            <Pie
              data={NEEDS_BY_TYPE}
              cx="50%" cy="50%"
              innerRadius={55} outerRadius={85}
              paddingAngle={3}
              dataKey="value"
            >
              {NEEDS_BY_TYPE.map((entry) => (
                <Cell key={entry.name} fill={entry.color} />
              ))}
            </Pie>
            <Tooltip formatter={(v) => [`${v}%`, '']} />
            <Legend
              iconType="circle"
              iconSize={8}
              formatter={(v) => <span style={{ color: '#94A3B8', fontSize: 12 }}>{v}</span>}
            />
          </PieChart>
        </ResponsiveContainer>
      </div>

      {/* Recent Activity */}
      <div className="chart-card glass-card">
        <h3 className="chart-title">Actividad Reciente</h3>
        <div className="activity-list">
          {RECENT_ACTIVITY.map((item, i) => (
            <div key={i} className={`activity-item activity-item--${item.type}`}>
              <span className="activity-icon">{item.icon}</span>
              <div className="activity-body">
                <p className="activity-text">{item.text}</p>
                <span className="activity-time">{item.time}</span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  </div>
);

export default AdminDashboard;
