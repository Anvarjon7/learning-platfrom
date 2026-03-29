import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthContext";
import { registerRequest } from "../auth/api";

export function SignUp() {
  const navigate = useNavigate();
  const { login } = useAuth();

  const [fullname, setFullname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError(null);
    setIsSubmitting(true);

    try {
      const token = await registerRequest({ fullname, email, password });
      login(token);
      navigate("/courses");
    } catch (err) {
      setError(err instanceof Error ? err.message : "Sign up failed");
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <div className="container">
      <h1>Sign up</h1>
      <form onSubmit={onSubmit} className="card">
        <label>
          Full name
          <input
            value={fullname}
            onChange={(e) => setFullname(e.target.value)}
            type="text"
            required
            autoComplete="name"
          />
        </label>
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
            autoComplete="new-password"
          />
        </label>

        <button type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Creating..." : "Create account"}
        </button>

        {error ? <div className="error">{error}</div> : null}
      </form>
    </div>
  );
}

