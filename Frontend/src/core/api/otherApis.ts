import type { Delivery, RegisterDeliveryDto } from '../types/shared.types';
import type { Need, CreateNeedDto } from '../types/need.types';
import type { Child, CreateChildDto } from '../types/child.types';
import type { Campaign, Volunteer, DashboardStats, MonthlyDelivery } from '../types/shared.types';
import httpClient from './httpClient';

// ── Needs ───────────────────────────────────────────────────────────────────
export const needsApi = {
  getAll: (status?: string, priority?: string) =>
    httpClient.get<Need[]>('/needs', { params: { status, priority } }).then((r) => r.data),

  create: (dto: CreateNeedDto) =>
    httpClient.post<Need>('/needs', dto).then((r) => r.data),

  updateStatus: (id: number, status: string) =>
    httpClient.patch<Need>(`/needs/${id}/status`, { status }).then((r) => r.data),
};

// ── Children ────────────────────────────────────────────────────────────────
export const childrenApi = {
  getAll: (familyId?: number) =>
    httpClient.get<Child[]>('/children', { params: { familyId } }).then((r) => r.data),

  create: (dto: CreateChildDto) =>
    httpClient.post<Child>('/children', dto).then((r) => r.data),
};

// ── Deliveries ──────────────────────────────────────────────────────────────
export const deliveriesApi = {
  getMyTasks: () =>
    httpClient.get<Delivery[]>('/deliveries/my-tasks').then((r) => r.data),

  getById: (id: number) =>
    httpClient.get<Delivery>(`/deliveries/${id}`).then((r) => r.data),

  register: (dto: RegisterDeliveryDto) => {
    const form = new FormData();
    form.append('deliveryId', String(dto.deliveryId));
    if (dto.notes) form.append('notes', dto.notes);
    if (dto.evidencePhoto) form.append('evidencePhoto', dto.evidencePhoto);
    return httpClient
      .post<Delivery>('/deliveries/register', form, {
        headers: { 'Content-Type': 'multipart/form-data' },
      })
      .then((r) => r.data);
  },

  updateStatus: (id: number, status: string) =>
    httpClient.patch<Delivery>(`/deliveries/${id}/status`, { status }).then((r) => r.data),
};

// ── Campaigns ───────────────────────────────────────────────────────────────
export const campaignsApi = {
  getAll: () =>
    httpClient.get<Campaign[]>('/campaigns').then((r) => r.data),

  getById: (id: number) =>
    httpClient.get<Campaign>(`/campaigns/${id}`).then((r) => r.data),
};

// ── Volunteers ──────────────────────────────────────────────────────────────
export const volunteersApi = {
  getAll: () =>
    httpClient.get<Volunteer[]>('/volunteers').then((r) => r.data),

  assignDelivery: (volunteerId: number, deliveryId: number) =>
    httpClient
      .post(`/volunteers/${volunteerId}/assign`, { deliveryId })
      .then((r) => r.data),
};

// ── Dashboard ───────────────────────────────────────────────────────────────
export const dashboardApi = {
  getStats: () =>
    httpClient.get<DashboardStats>('/dashboard/stats').then((r) => r.data),

  getMonthlyDeliveries: () =>
    httpClient.get<MonthlyDelivery[]>('/dashboard/monthly-deliveries').then((r) => r.data),
};
