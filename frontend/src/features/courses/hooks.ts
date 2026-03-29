import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  type CourseDetails,
  type CourseResponse,
  type CreateCourseInput,
  createCourse,
  fetchCourseDetails,
  fetchCourses,
} from "./api";

export function useCoursesQuery() {
  return useQuery<CourseResponse[]>({
    queryKey: ["courses"],
    queryFn: fetchCourses,
  });
}

export function useCourseDetailsQuery(courseId: string | undefined) {
  const parsedId = courseId ? Number(courseId) : NaN;
  const enabled = Number.isFinite(parsedId) && parsedId > 0;

  return useQuery<CourseDetails>({
    queryKey: ["courseDetails", courseId],
    queryFn: () => fetchCourseDetails(parsedId),
    enabled,
  });
}

export function useCreateCourseMutation() {
  const queryClient = useQueryClient();

  return useMutation<CourseResponse, unknown, CreateCourseInput>({
    mutationFn: createCourse,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["courses"] });
    },
  });
}

