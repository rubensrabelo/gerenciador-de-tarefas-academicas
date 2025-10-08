import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import CreateTask from "../pages/task/TaskCreate";
import TaskList from "../pages/task/TaskList";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/tasks" />} />
        <Route path="/tasks" element={<TaskList />} />
        <Route path="/tasks/create" element={<CreateTask />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
