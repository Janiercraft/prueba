import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import ProtectedRoute from '../core/auth/ProtectedRoute';

// ── Auth ────────────────────────────────────────────────────────────────────
import LoginPage from '../features/auth/LoginPage';
import UnauthorizedPage from '../features/auth/UnauthorizedPage';

// ── Layouts ─────────────────────────────────────────────────────────────────
import AdminLayout from '../layouts/AdminLayout';
import VolunteerLayout from '../layouts/VolunteerLayout';
import DonorLayout from '../layouts/DonorLayout';

// ── Admin pages ─────────────────────────────────────────────────────────────
import AdminDashboard from '../features/dashboard/AdminDashboard';
import FamiliesPage from '../features/families/FamiliesPage';
import FamilyDetailPage from '../features/families/FamilyDetailPage';
import ChildrenPage from '../features/children/ChildrenPage';
import NeedsPage from '../features/needs/NeedsPage';
import DonationsPage from '../features/donations/DonationsPage';
import InventoryPage from '../features/inventory/InventoryPage';
import InventoryDetailPage from '../features/inventory/InventoryDetailPage';
import CampaignsPage from '../features/campaigns/CampaignsPage';
import VolunteersPage from '../features/volunteers/VolunteersPage';
import ImpactAdminPage from '../features/impact/ImpactAdminPage';

// ── Volunteer pages ──────────────────────────────────────────────────────────
import TasksPage from '../features/deliveries/TasksPage';
import TaskDetailPage from '../features/deliveries/TaskDetailPage';
import VolunteerFamiliesPage from '../features/families/VolunteerFamiliesPage';

// ── Donor pages ──────────────────────────────────────────────────────────────
import DonorHomePage from '../features/campaigns/DonorHomePage';
import DonatePage from '../features/donations/DonatePage';
import MyDonationsPage from '../features/donations/MyDonationsPage';
import MyImpactPage from '../features/impact/MyImpactPage';

const AppRouter = () => (
  <BrowserRouter>
    <Routes>
      {/* ── Públicas ─────────────────────────────────────────────────────── */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/unauthorized" element={<UnauthorizedPage />} />
      <Route path="/" element={<Navigate to="/login" replace />} />

      {/* ── Admin ────────────────────────────────────────────────────────── */}
      <Route element={<ProtectedRoute allowedRoles={['ADMIN']} />}>
        <Route element={<AdminLayout />}>
          <Route path="/admin/dashboard" element={<AdminDashboard />} />
          <Route path="/admin/families" element={<FamiliesPage />} />
          <Route path="/admin/families/:id" element={<FamilyDetailPage />} />
          <Route path="/admin/children" element={<ChildrenPage />} />
          <Route path="/admin/needs" element={<NeedsPage />} />
          <Route path="/admin/donations" element={<DonationsPage />} />
          <Route path="/admin/inventory" element={<InventoryPage />} />
          <Route path="/admin/inventory/:id" element={<InventoryDetailPage />} />
          <Route path="/admin/campaigns" element={<CampaignsPage />} />
          <Route path="/admin/volunteers" element={<VolunteersPage />} />
          <Route path="/admin/impact" element={<ImpactAdminPage />} />
        </Route>
      </Route>

      {/* ── Volunteer ────────────────────────────────────────────────────── */}
      <Route element={<ProtectedRoute allowedRoles={['VOLUNTEER']} />}>
        <Route element={<VolunteerLayout />}>
          <Route path="/volunteer/tasks" element={<TasksPage />} />
          <Route path="/volunteer/tasks/:id" element={<TaskDetailPage />} />
          <Route path="/volunteer/families" element={<VolunteerFamiliesPage />} />
        </Route>
      </Route>

      {/* ── Donor ────────────────────────────────────────────────────────── */}
      <Route element={<ProtectedRoute allowedRoles={['DONOR']} />}>
        <Route element={<DonorLayout />}>
          <Route path="/donor/home" element={<DonorHomePage />} />
          <Route path="/donor/donate" element={<DonatePage />} />
          <Route path="/donor/my-donations" element={<MyDonationsPage />} />
          <Route path="/donor/my-impact" element={<MyImpactPage />} />
        </Route>
      </Route>
    </Routes>
  </BrowserRouter>
);

export default AppRouter;
