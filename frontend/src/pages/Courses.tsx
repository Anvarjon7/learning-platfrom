import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { useCoursesQuery } from "../features/courses/hooks";
import { useAuth } from "../auth/AuthContext";

export function Courses() {
  const navigate = useNavigate();
  const { logout } = useAuth();

  const { data, isLoading, error } = useCoursesQuery();

  function onLogout() {
    logout();
    navigate("/");
  }

  return (
    <div className="container">
      <div className="row" style={{ justifyContent: "space-between" }}>
        <h1 style={{ margin: 0 }}>Courses</h1>
        <div className="row">
          <Link to="/create-course">Create course</Link>
          <button type="button" onClick={onLogout}>
            Logout
          </button>
        </div>
      </div>

      {isLoading ? <div style={{ marginTop: 16 }}>Loading courses...</div> : null}

      {error ? (
        <div className="error" style={{ marginTop: 16 }}>
          Failed to load courses.
        </div>
      ) : null}

      {!isLoading && data ? (
        <div style={{ marginTop: 16, display: "grid", gap: 12 }}>
          {data.map((course) => (
            <Link key={course.id} to={`/courses/${course.id}`} className="card">
              <h3 style={{ marginTop: 0 }}>{course.title}</h3>
              <div style={{ color: "#374151" }}>{course.description}</div>
            </Link>
          ))}
        </div>
      ) : null}
    </div>
  );
}

