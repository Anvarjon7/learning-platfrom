import React, { useMemo, useState } from "react";
import { type QuizLessonDTO } from "../courses/api";
import { useSubmitQuizMutation } from "./hooks";

function scoreLabel(score: number, passed: boolean) {
  if (passed) return `Passed (${score}/${100})`;
  return `Not passed (${score}/${100})`;
}

export function QuizLessonViewer({ lesson }: { lesson: QuizLessonDTO }) {
  const { mutateAsync, isPending, error, data } = useSubmitQuizMutation();

  const [selectedByQuestionId, setSelectedByQuestionId] = useState<
    Record<number, number | undefined>
  >({});
  const [submitError, setSubmitError] = useState<string | null>(null);

  const allAnswered = useMemo(() => {
    return lesson.questions.every((q) => selectedByQuestionId[q.id] !== undefined);
  }, [lesson.questions, selectedByQuestionId]);

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setSubmitError(null);

    if (!allAnswered) {
      setSubmitError("Please answer all questions before submitting.");
      return;
    }

    const payload = {
      answers: lesson.questions.map((q) => ({
        questionId: q.id,
        selectedAnswerIndex: selectedByQuestionId[q.id] as number,
      })),
    };

    try {
      await mutateAsync({ lessonId: lesson.id, payload });
    } catch (err) {
      setSubmitError(err instanceof Error ? err.message : "Quiz submission failed");
    }
  }

  return (
    <div className="card">
      <h3 style={{ marginTop: 0 }}>{lesson.title}</h3>
      {lesson.description ? <div style={{ color: "#374151" }}>{lesson.description}</div> : null}

      <form onSubmit={onSubmit} style={{ marginTop: 12 }}>
        {lesson.questions.map((q, idx) => {
          const selected = selectedByQuestionId[q.id];
          return (
            <div key={q.id} className="card" style={{ marginBottom: 12, padding: 12 }}>
              <div style={{ fontWeight: 700 }}>
                Question {idx + 1}
              </div>
              <div style={{ marginTop: 6 }}>{q.prompt}</div>

              <div style={{ marginTop: 10, display: "grid", gap: 8 }}>
                {q.choices.map((choice, choiceIdx) => {
                  const inputId = `q-${q.id}-c-${choiceIdx}`;
                  return (
                    <label key={choiceIdx} htmlFor={inputId} style={{ display: "flex", gap: 8 }}>
                      <input
                        id={inputId}
                        type="radio"
                        name={`q-${q.id}`}
                        value={choiceIdx}
                        checked={selected === choiceIdx}
                        onChange={() =>
                          setSelectedByQuestionId((prev) => ({
                            ...prev,
                            [q.id]: choiceIdx,
                          }))
                        }
                      />
                      <span>{choice}</span>
                    </label>
                  );
                })}
              </div>
            </div>
          );
        })}

        {submitError ? <div className="error">{submitError}</div> : null}
        {error ? <div className="error">Quiz submission failed.</div> : null}

        <button type="submit" disabled={isPending || !allAnswered}>
          {isPending ? "Submitting..." : "Submit quiz"}
        </button>

        {data ? (
          <div style={{ marginTop: 14 }}>
            <div style={{ fontWeight: 700 }}>{scoreLabel(data.score, data.passed)}</div>
            <div style={{ color: "#374151", marginTop: 6 }}>
              Total questions: {data.totalQuestions}
            </div>
          </div>
        ) : null}
      </form>
    </div>
  );
}

