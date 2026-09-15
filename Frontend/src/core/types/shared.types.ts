export type DeliveryStatus = 'PENDING' | 'IN_TRANSIT' | 'DELIVERED';

export interface Delivery {
  id: number;
  resourceId: number;
  resourceCode: string;
  familyId: number;
  familyName: string;
  familyAddress: string;
  volunteerId: number;
  volunteerName: string;
  needId: number;
  status: DeliveryStatus;
  scheduledDate: string;
  deliveredAt?: string;
  evidencePhotoUrl?: string;
  notes?: string;
}

export interface RegisterDeliveryDto {
  deliveryId: number;
  notes?: string;
  evidencePhoto?: File;
}

export interface Campaign {
  id: number;
  name: string;
  description: string;
  startDate: string;
  endDate: string;
  goal: number;
  collected: number;
  status: 'ACTIVE' | 'CLOSED';
  donationsCount: number;
}

export interface Volunteer {
  id: number;
  name: string;
  phone: string;
  email: string;
  supportArea: string;
  status: 'ACTIVE' | 'INACTIVE';
  pendingTasksCount: number;
}

export interface DashboardStats {
  totalFamilies: number;
  totalChildren: number;
  availableResources: number;
  attendedNeeds: number;
  pendingNeeds: number;
  totalDonations: number;
  activeCampaigns: number;
  activeVolunteers: number;
}

export interface MonthlyDelivery {
  month: string;
  delivered: number;
  pending: number;
}

export interface NeedsByType {
  type: string;
  count: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}

export interface ApiError {
  message: string;
  status: number;
  errors?: Record<string, string>;
}
