import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useCreateCourseMutation } from "../features/courses/hooks";

export function CreateCourse() {
  const navigate = useNavigate();

  const mutation = useCreateCourseMutation();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [localError, setLocalError] = useState<string | null>(null);

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setLocalError(null);

    mutation.mutate(
      { title, description },
      {
        onSuccess: () => {
          navigate("/courses");
        },
        onError: (err) => {
          const message = err instanceof Error ? err.message : "Failed to create course";
          setLocalError(message);
        },
      },
    );
  }

  return (
    <div className="container">
      <h1>Create Course</h1>
      <form onSubmit={onSubmit} className="card">
        <label>
          Title
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            type="text"
            required
          />
        </label>
        <label>
          Description
          <textarea
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
            rows={6}
          />
        </label>

        <button type="submit" disabled={mutation.isPending}>
          {mutation.isPending ? "Creating..." : "Create"}
        </button>

        {localError ? <div className="error">{localError}</div> : null}
      </form>
    </div>
  );
}

