import { useMutation } from "@tanstack/react-query";
import { completeLesson } from "./api";

export function useCompleteLessonMutation() {
  return useMutation({
    mutationFn: (lessonId: number) => completeLesson(lessonId),
  });
}

