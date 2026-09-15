export type FamilyStatus = 'ACTIVE' | 'INACTIVE';

export interface Family {
  id: number;
  contactName: string;
  phone: string;
  municipality: string;
  address: string;
  status: FamilyStatus;
  registrationDate: string;
  childrenCount?: number;
  attendedNeedsCount?: number;
}

export interface FamilyPreview {
  id: number;
  contactName: string;
  municipality: string;
  status: FamilyStatus;
  childrenCount: number;
}

export interface CreateFamilyDto {
  contactName: string;
  phone: string;
  municipality: string;
  address: string;
}

export interface UpdateFamilyDto extends Partial<CreateFamilyDto> {
  status?: FamilyStatus;
}
