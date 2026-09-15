import type { Donation, CreateDonationDto, Resource, TraceabilityChain } from '../types/donation.types';
import type { PaginatedResponse } from '../types/shared.types';
import httpClient from './httpClient';

export const donationsApi = {
  getAll: (page = 0, size = 10) =>
    httpClient
      .get<PaginatedResponse<Donation>>('/donations', { params: { page, size } })
      .then((r) => r.data),

  getById: (id: number) =>
    httpClient.get<Donation>(`/donations/${id}`).then((r) => r.data),

  create: (dto: CreateDonationDto) =>
    httpClient.post<Donation>('/donations', dto).then((r) => r.data),

  getMyDonations: () =>
    httpClient.get<Donation[]>('/donations/my').then((r) => r.data),

  getTraceability: (donationId: number) =>
    httpClient.get<TraceabilityChain[]>(`/donations/${donationId}/traceability`).then((r) => r.data),
};

export const resourcesApi = {
  getAll: (status?: string) =>
    httpClient.get<Resource[]>('/resources', { params: { status } }).then((r) => r.data),

  getById: (id: number) =>
    httpClient.get<Resource>(`/resources/${id}`).then((r) => r.data),

  assign: (resourceId: number, familyId: number, needId: number) =>
    httpClient
      .post(`/resources/${resourceId}/assign`, { familyId, needId })
      .then((r) => r.data),

  getTraceability: (resourceId: number) =>
    httpClient.get<TraceabilityChain>(`/resources/${resourceId}/traceability`).then((r) => r.data),
};
