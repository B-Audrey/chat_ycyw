export interface User {
  uuid?: string;
  email: string;
  firstName: string;
  lastName: string;
  password?: string;
  role: string;
  createdAt?: Date;
  updatedAt?: Date;
  deletedAt?: Date | null;
}
