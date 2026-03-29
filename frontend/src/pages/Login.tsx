import { useAuth } from "../auth/AuthContext";
import { Link, useNavigate } from "react-router-dom";
import React, { useState } from "react";
import { loginRequest } from "../auth/api";
import type { LoginRequest } from "../auth/api";

export function Login() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setIsSubmitting(true);

    try {
      const payload: LoginRequest = { email, password };
      const token = await loginRequest(payload);
      login(token);
      navigate("/courses");
    } catch (err) {
      setError(err instanceof Error ? err.message : "Login failed");
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <div className="container">
      <h1>Login</h1>
      <form onSubmit={onSubmit} className="card">
        <label>
          Email
          <input
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            type="email"
            required
            autoComplete="username"
          />
        </label>
        <label>
          Password
          <input
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            type="password"
            required
            autoComplete="current-password"
          />
        </label>
        <button disabled={isSubmitting} type="submit">
          {isSubmitting ? "Signing in..." : "Sign in"}
        </button>
        {error ? <div className="error">{error}</div> : null}
      </form>

      <div style={{ marginTop: 14, color: "#374151" }}>
        New here?{" "}
        <Link to="/signup" style={{ fontWeight: 700 }}>
          Create an account
        </Link>
      </div>
    </div>
  );
}

