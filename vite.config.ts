import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  // Keep the existing Spring Boot backend intact; the frontend lives in ./frontend
  root: "frontend",
  plugins: [react()],
  server: {
    proxy: {
      // Avoid browser CORS issues by proxying /api to the Spring Boot backend.
      "/api": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});

