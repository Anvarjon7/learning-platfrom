import { apiClient } from "../../api/client";

export interface CourseResponse {
  id: number;
  title: string;
  description: string;
  category?: string;
  level?: string;
  tutorEmail?: string;
}

export interface ModuleResponse {
  id: number;
  title: string;
  description?: string;
  // Backend's ModuleResponse doesn't populate lessons; we build it in the frontend.
  lessons?: LessonResponse[] | null;
}

export type LessonType = "TEXT" | "PDF" | "VIDEO" | "QUIZ";

export interface TextLessonDTO {
  id: number;
  title: string;
  description?: string;
  content: string;
  type: "TEXT";
  tutorEmail?: string;
}

export interface PdfLessonDTO {
  id: number;
  title: string;
  description?: string;
  numberOfPages?: number;
  pdfUrl: string;
  type: "PDF";
}

export interface VideoLessonDTO {
  id: number;
  title: string;
  description?: string;
  durationSeconds?: number;
  videoUrl: string;
  type: "VIDEO";
}

export interface QuestionDTO {
  id: number;
  prompt: string;
  choices: string[];
  correctAnswerIndex: number | null;
}

export interface QuizLessonDTO {
  id: number;
  title: string;
  description?: string;
  questions: QuestionDTO[];
  type: "QUIZ";
}

export type LessonResponse =
  | { type: "TEXT"; lesson: TextLessonDTO }
  | { type: "PDF"; lesson: PdfLessonDTO }
  | { type: "VIDEO"; lesson: VideoLessonDTO }
  | { type: "QUIZ"; lesson: QuizLessonDTO };

export interface ModuleWithLessons extends Omit<ModuleResponse, "lessons"> {
  lessons: LessonResponse[];
}

export interface CourseDetails {
  course: CourseResponse;
  modules: ModuleWithLessons[];
}

export interface CreateCourseInput {
  title: string;
  description: string;
  // Backend DTO supports these fields; we default them to avoid null issues.
  category?: string;
  level?: string;
}

export async function fetchCourses(): Promise<CourseResponse[]> {
  return apiClient.get<CourseResponse[]>("/courses").then((r) => r.data);
}

export async function createCourse(input: CreateCourseInput): Promise<CourseResponse> {
  const payload = {
    title: input.title,
    description: input.description,
    category: input.category ?? "",
    level: input.level ?? "",
  };

  return apiClient.post<CourseResponse>("/courses", payload).then((r) => r.data);
}

export async function fetchCourseById(courseId: number): Promise<CourseResponse> {
  return apiClient.get<CourseResponse>(`/courses/${courseId}`).then((r) => r.data);
}

export async function fetchModulesByCourseId(courseId: number): Promise<ModuleResponse[]> {
  return apiClient
    .get<ModuleResponse[]>(`/courses/${courseId}/modules`)
    .then((r) => r.data);
}

export async function fetchLessonsByModuleId(moduleId: number): Promise<LessonResponse[]> {
  return apiClient
    .get<LessonResponse[]>(`/modules/${moduleId}/lessons`)
    .then((r) => r.data);
}

export async function fetchCourseDetails(courseId: number): Promise<CourseDetails> {
  const [course, modules] = await Promise.all([
    fetchCourseById(courseId),
    fetchModulesByCourseId(courseId),
  ]);

  const modulesWithLessons: ModuleWithLessons[] = await Promise.all(
    modules.map(async (m) => {
      const lessons = await fetchLessonsByModuleId(m.id);
      return {
        id: m.id,
        title: m.title,
        description: m.description,
        lessons,
      };
    }),
  );

  return { course, modules: modulesWithLessons };
}

