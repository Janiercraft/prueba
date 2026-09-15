import type { NeedType } from './need.types';

export type DonationType = NeedType | 'MONEY';
export type DonationStatus = 'RECEIVED' | 'IN_INVENTORY' | 'ASSIGNED' | 'DELIVERED';
export type ResourceStatus = 'AVAILABLE' | 'ASSIGNED' | 'DELIVERED';

export interface Donation {
  id: number;
  donorId: number;
  donorName: string;
  campaignId?: number;
  campaignName?: string;
  type: DonationType;
  value?: number;      // solo si tipo === 'MONEY'
  quantity?: number;   // solo si tipo !== 'MONEY'
  description: string;
  date: string;
  status: DonationStatus;
}

export interface CreateDonationDto {
  donorId?: number;     // opcional si el donante está autenticado
  campaignId?: number;
  type: DonationType;
  value?: number;
  quantity?: number;
  description: string;
}

export interface Resource {
  id: number;
  code: string;        // ej: PC-00052
  type: DonationType;
  description: string;
  status: ResourceStatus;
  donationId: number;
  entryDate: string;
}

export interface TraceabilityChain {
  donor: { id: number; name: string };
  donation: { id: number; type: string; date: string };
  resource: { id: number; code: string };
  assignment?: { id: number; familyName: string; date: string };
  delivery?: { id: number; date: string; volunteerName: string };
  beneficiary?: { id: number; childName: string; age: number };
}
