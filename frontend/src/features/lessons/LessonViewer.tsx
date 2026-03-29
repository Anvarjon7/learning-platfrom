import React, { useState } from "react";
import {
  type LessonResponse,
  type PdfLessonDTO,
  type TextLessonDTO,
  type VideoLessonDTO,
} from "../courses/api";
import { useCompleteLessonMutation } from "./hooks";
import { QuizLessonViewer } from "../quizzes/QuizLessonViewer";

type LessonViewerProps = {
  lesson: LessonResponse;
};

export function LessonViewer({ lesson }: LessonViewerProps) {
  if (lesson.type === "TEXT") {
    const l = lesson.lesson as TextLessonDTO;
    return <TextLessonCard lesson={l} />;
  }

  if (lesson.type === "PDF") {
    const l = lesson.lesson as PdfLessonDTO;
    return <PdfLessonCard lesson={l} />;
  }

  if (lesson.type === "VIDEO") {
    const l = lesson.lesson as VideoLessonDTO;
    return <VideoLessonCard lesson={l} />;
  }

  return <QuizLessonViewer lesson={lesson.lesson} />;
}

function TextLessonCard({ lesson }: { lesson: TextLessonDTO }) {
  const completeMutation = useCompleteLessonMutation();
  const [completed, setCompleted] = useState(false);
  const [localError, setLocalError] = useState<string | null>(null);

  async function onComplete() {
    setLocalError(null);
    try {
      await completeMutation.mutateAsync(lesson.id);
      setCompleted(true);
    } catch (err) {
      setLocalError(err instanceof Error ? err.message : "Failed to complete lesson");
    }
  }

  return (
    <div className="card">
      <h3 style={{ marginTop: 0 }}>{lesson.title}</h3>
      <p>{lesson.content}</p>

      {completed ? (
        <div style={{ marginTop: 12, fontWeight: 700 }}>Completed</div>
      ) : (
        <>
          <button type="button" onClick={onComplete} disabled={completeMutation.isPending}>
            {completeMutation.isPending ? "Completing..." : "Mark as completed"}
          </button>
          {localError ? <div className="error">{localError}</div> : null}
        </>
      )}
    </div>
  );
}

function PdfLessonCard({ lesson }: { lesson: PdfLessonDTO }) {
  const completeMutation = useCompleteLessonMutation();
  const [completed, setCompleted] = useState(false);
  const [localError, setLocalError] = useState<string | null>(null);

  async function onComplete() {
    setLocalError(null);
    try {
      await completeMutation.mutateAsync(lesson.id);
      setCompleted(true);
    } catch (err) {
      setLocalError(err instanceof Error ? err.message : "Failed to complete lesson");
    }
  }

  return (
    <div className="card">
      <h3 style={{ marginTop: 0 }}>{lesson.title}</h3>
      <iframe
        src={lesson.pdfUrl}
        title={lesson.title}
        style={{ width: "100%", height: 520, border: "0" }}
      />

      {completed ? (
        <div style={{ marginTop: 12, fontWeight: 700 }}>Completed</div>
      ) : (
        <>
          <button type="button" onClick={onComplete} disabled={completeMutation.isPending}>
            {completeMutation.isPending ? "Completing..." : "Mark as completed"}
          </button>
          {localError ? <div className="error">{localError}</div> : null}
        </>
      )}
    </div>
  );
}

function VideoLessonCard({ lesson }: { lesson: VideoLessonDTO }) {
  const completeMutation = useCompleteLessonMutation();
  const [completed, setCompleted] = useState(false);
  const [localError, setLocalError] = useState<string | null>(null);

  async function onComplete() {
    setLocalError(null);
    try {
      await completeMutation.mutateAsync(lesson.id);
      setCompleted(true);
    } catch (err) {
      setLocalError(err instanceof Error ? err.message : "Failed to complete lesson");
    }
  }

  return (
    <div className="card">
      <h3 style={{ marginTop: 0 }}>{lesson.title}</h3>
      <video controls style={{ width: "100%" }}>
        <source src={lesson.videoUrl} />
        Your browser does not support the video tag.
      </video>

      {completed ? (
        <div style={{ marginTop: 12, fontWeight: 700 }}>Completed</div>
      ) : (
        <>
          <button type="button" onClick={onComplete} disabled={completeMutation.isPending}>
            {completeMutation.isPending ? "Completing..." : "Mark as completed"}
          </button>
          {localError ? <div className="error">{localError}</div> : null}
        </>
      )}
    </div>
  );
}

