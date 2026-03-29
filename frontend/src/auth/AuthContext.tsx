import React, { createContext, useCallback, useContext, useMemo, useState } from "react";
import { getStoredToken } from "../api/client";

const TOKEN_KEY = "ai_academy_token";

export type AuthContextValue = {
  token: string | null;
  login: (token: string) => void;
  logout: () => void;
};

const AuthContext = createContext<AuthContextValue | null>(null);

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(() => getStoredToken());

  const login = useCallback((nextToken: string) => {
    setToken(nextToken);
    try {
      window.localStorage.setItem(TOKEN_KEY, nextToken);
    } catch {
      // Ignore localStorage failures; token still works for this session.
    }
  }, []);

  const logout = useCallback(() => {
    setToken(null);
    try {
      window.localStorage.removeItem(TOKEN_KEY);
    } catch {
      // Ignore localStorage failures.
    }
  }, []);

  const value = useMemo<AuthContextValue>(
    () => ({ token, login, logout }),
    [token, login, logout],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth(): AuthContextValue {
  const ctx = useContext(AuthContext);
  if (!ctx) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return ctx;
}

