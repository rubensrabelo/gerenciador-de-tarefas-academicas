import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import CreateTask from "../pages/task/TaskCreate";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/tasks/create" />} />
        <Route path="/tasks/create" element={<CreateTask />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
