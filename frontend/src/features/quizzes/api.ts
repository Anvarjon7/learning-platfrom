import { apiClient } from "../../api/client";

export interface QuizAnswerSubmission {
  questionId: number;
  selectedAnswerIndex: number;
}

export interface QuizSubmissionRequest {
  answers: QuizAnswerSubmission[];
}

export interface QuizSubmissionResponse {
  score: number;
  passed: boolean;
  totalQuestions: number;
}

export async function submitQuizLesson(
  lessonId: number,
  payload: QuizSubmissionRequest,
): Promise<QuizSubmissionResponse> {
  const res = await apiClient.post<QuizSubmissionResponse>(
    `/lessons/${lessonId}/quiz/submit`,
    payload,
  );
  return res.data;
}

