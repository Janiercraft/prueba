export type NeedType =
  | 'COMPUTER'
  | 'TABLET'
  | 'SCHOOL_KIT'
  | 'FOOD'
  | 'CLOTHING'
  | 'OTHER';

export type NeedStatus = 'PENDING' | 'IN_PROGRESS' | 'ATTENDED';
export type Priority = 'HIGH' | 'MEDIUM' | 'LOW';

export interface Need {
  id: number;
  familyId: number;
  familyName: string;
  type: NeedType;
  description: string;
  priority: Priority;
  status: NeedStatus;
  createdAt: string;
  attendedAt?: string;
}

export interface NeedPreview {
  id: number;
  familyName: string;
  type: NeedType;
  priority: Priority;
  status: NeedStatus;
  createdAt: string;
}

export interface CreateNeedDto {
  familyId: number;
  type: NeedType;
  description: string;
  priority: Priority;
}
