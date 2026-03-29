import { apiClient } from "../api/client";

export interface LoginRequest {
  email: string;
  password: string;
}

export async function loginRequest(payload: LoginRequest): Promise<string> {
  // Backend returns a raw token string as the response body.
  const res = await apiClient.post<string>("/auth/login", payload);
  return res.data;
}

export interface RegisterRequest {
  email: string;
  password: string;
  fullname: string;
  roles?: string[];
}

export async function registerRequest(payload: RegisterRequest): Promise<string> {
  const res = await apiClient.post<string>("/auth/register", payload);
  return res.data;
}

