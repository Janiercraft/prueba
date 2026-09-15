export interface Child {
  id: number;
  familyId: number;
  familyName: string;
  name: string;
  birthDate: string;
  age?: number;
  educationalInstitution: string;
  grade: string;
  status: 'ACTIVE' | 'INACTIVE';
}

export interface CreateChildDto {
  familyId: number;
  name: string;
  birthDate: string;
  educationalInstitution: string;
  grade: string;
}
