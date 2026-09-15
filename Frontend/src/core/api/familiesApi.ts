import type { Family, CreateFamilyDto, UpdateFamilyDto } from '../types/family.types';
import type { PaginatedResponse } from '../types/shared.types';
import httpClient from './httpClient';

export const familiesApi = {
  getAll: (page = 0, size = 10, municipality?: string) =>
    httpClient
      .get<PaginatedResponse<Family>>('/families', { params: { page, size, municipality } })
      .then((r) => r.data),

  getById: (id: number) =>
    httpClient.get<Family>(`/families/${id}`).then((r) => r.data),

  create: (dto: CreateFamilyDto) =>
    httpClient.post<Family>('/families', dto).then((r) => r.data),

  update: (id: number, dto: UpdateFamilyDto) =>
    httpClient.put<Family>(`/families/${id}`, dto).then((r) => r.data),

  deactivate: (id: number) =>
    httpClient.patch<Family>(`/families/${id}/status`, { status: 'INACTIVE' }).then((r) => r.data),
};
