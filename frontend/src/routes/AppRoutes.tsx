import React from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import { Login } from "../pages/Login";
import { SignUp } from "../pages/SignUp";
import { Courses } from "../pages/Courses";
import { CourseDetails } from "../pages/CourseDetails";
import { CreateCourse } from "../pages/CreateCourse";

export function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/courses" element={<Courses />} />
      <Route path="/create-course" element={<CreateCourse />} />
      <Route path="/courses/:id" element={<CourseDetails />} />
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}

