import React from "react";
import { useParams, Link } from "react-router-dom";
import { LessonViewer } from "../features/lessons/LessonViewer";
import { useCourseDetailsQuery } from "../features/courses/hooks";

export function CourseDetails() {
  const params = useParams();
  const courseId = params.id;

  const { data, isLoading, error } = useCourseDetailsQuery(courseId);

  return (
    <div className="container">
      <div style={{ display: "flex", gap: 12, alignItems: "center" }}>
        <Link to="/courses">← Back to courses</Link>
      </div>

      {isLoading ? <div style={{ marginTop: 16 }}>Loading course...</div> : null}

      {error ? (
        <div className="error" style={{ marginTop: 16 }}>
          Failed to load course details.
        </div>
      ) : null}

      {!isLoading && data ? (
        <div style={{ marginTop: 16 }}>
          <div className="card">
            <h1 style={{ marginTop: 0 }}>{data.course.title}</h1>
            <div style={{ color: "#374151" }}>{data.course.description}</div>
          </div>

          <div style={{ marginTop: 16 }}>
            {data.modules.length === 0 ? (
              <div>No modules found.</div>
            ) : (
              <div style={{ display: "grid", gap: 16 }}>
                {data.modules.map((module) => (
                  <div key={module.id} className="card">
                    <h2 style={{ marginTop: 0 }}>{module.title}</h2>
                    {module.description ? (
                      <div style={{ color: "#374151" }}>{module.description}</div>
                    ) : null}

                    <div style={{ marginTop: 12, display: "grid", gap: 12 }}>
                      {module.lessons.map((lesson, idx) => (
                        <LessonViewer key={`${module.id}-${lesson.type}-${idx}`} lesson={lesson} />
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      ) : null}
    </div>
  );
}

