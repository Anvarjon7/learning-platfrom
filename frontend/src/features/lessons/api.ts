import { apiClient } from "../../api/client";

export async function completeLesson(lessonId: number): Promise<void> {
  await apiClient.post<void>(`/lessons/${lessonId}/complete`);
}

