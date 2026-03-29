import { useMutation } from "@tanstack/react-query";
import { type QuizSubmissionRequest, submitQuizLesson } from "./api";

export function useSubmitQuizMutation() {
  return useMutation({
    mutationFn: (params: { lessonId: number; payload: QuizSubmissionRequest }) =>
      submitQuizLesson(params.lessonId, params.payload),
  });
}

