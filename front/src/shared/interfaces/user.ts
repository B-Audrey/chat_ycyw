export interface User {
  uuid: string;
  email: string;
  password?: string;
  lastName?: string;
  firstName?: string;
  adresse?: string;
  city?: string;
  postalCode?: string;
  country?: string;
  complementaryAdress?: string;
  adressNumber?: string;
  birthDate?: string; // ISO format
  striped?: string;
  lastConnectionAt?: string;

  roles: Role[];

  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string;
}
export enum Role {
  ROLE_USER = 'ROLE_USER',
  ROLE_COLLABORATOR = 'ROLE_COLLABORATOR',
  ROLE_ADMIN = 'ROLE_ADMIN',
  ROLE_SUPER_ADMIN = 'ROLE_SUPER_ADMIN',
}
