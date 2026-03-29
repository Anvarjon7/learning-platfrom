import axios, { AxiosError, type AxiosRequestConfig } from "axios";

const TOKEN_KEY = "ai_academy_token";

export function getStoredToken(): string | null {
  try {
    const token = window.localStorage.getItem(TOKEN_KEY);
    return token ? token : null;
  } catch {
    // localStorage can fail in some environments (private mode, etc).
    return null;
  }
}

function getApiBaseUrl(): string {
  // Prefer the exact URL requirement, but fall back to a relative dev base
  // to reduce CORS friction during local development.
  const fromEnv = import.meta.env.VITE_API_BASE_URL as string | undefined;
  if (fromEnv && fromEnv.trim().length > 0) return fromEnv;

  if (import.meta.env.DEV) return "/api";
  return "http://localhost:8080/api";
}

export const apiClient = axios.create({
  baseURL: getApiBaseUrl(),
});

apiClient.interceptors.request.use((config) => {
  const token = getStoredToken();
  if (!token) return config;

  const headers = (config.headers ?? {}) as Record<string, string>;
  headers.Authorization = `Bearer ${token}`;
  config.headers = headers;
  return config;
});

apiClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    // If token is missing/expired, the backend will return 401.
    // We intentionally don't auto-logout here to keep auth policy inside AuthContext.
    if (error.response?.status === 401) {
      // Keep it silent/minimal; callers can still react to errors via React Query.
    }
    return Promise.reject(error);
  },
);

export async function apiRequest<T>(
  config: AxiosRequestConfig,
): Promise<T> {
  const res = await apiClient.request<T>(config);
  return res.data;
}

