import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import styles from "./TaskList.module.css";

import type { TaskResponse } from "../../api/models/interface/task/task-response.interface";
import { TaskError } from "../../api/services/errors/task.error";
import { getAll } from "../../api/services/task/task-find-all.service";
import { deleteTask } from "../../api/services/task/task-delete.service";

function TaskList() {
  const [tasks, setTasks] = useState<TaskResponse[]>([]);
  const [page, setPage] = useState<number>(0);
  const [totalPages, setTotalPages] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

  async function loadTasks(currentPage = 0) {
    try {
      setLoading(true);
      setError(null);

      const data = await getAll({ page: currentPage, size: 5, direction: "desc" });
      setTasks(data.content);
      setTotalPages(data.totalPages);
      setPage(data.number);
    } catch (err: any) {
      if (err instanceof TaskError) {
        setError(err.message);
      } else {
        setError("Failed to load tasks.");
      }
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadTasks();
  }, []);

  const handlePrevPage = () => {
    if (page > 0) loadTasks(page - 1);
  };

  const handleNextPage = () => {
    if (page + 1 < totalPages) loadTasks(page + 1);
  };

  const handleCreateTask = () => {
    navigate("/tasks/create");
  };

  const handleEdit = (id: number) => {
    navigate(`/tasks/edit/${id}`);
  };

  const handleDelete = async (id: number) => {
    if (!confirm("Are you sure you want to delete this task?")) return;
    try {
      await deleteTask(id);
      loadTasks(page); // recarrega a lista
    } catch (err: any) {
      alert("Failed to delete task.");
    }
  };

  return (
    <div className={styles.container}>
      <h1>All Tasks</h1>

      <div className={styles.actions}>
        <button className={styles.createBtn} onClick={handleCreateTask}>
          + Create Task
        </button>
      </div>

      {loading && <p>Loading...</p>}
      {error && <p className={styles.error}>{error}</p>}
      {!loading && tasks.length === 0 && <p>No tasks found.</p>}

      <ul className={styles.taskList}>
        {tasks.map((task) => (
          <li key={task.id} className={styles.taskCard}>
            <div
              className={styles.taskInfo}
              onClick={() => navigate(`/tasks/${task.id}`)}
            >
              <h3>{task.title}</h3>
              <p>{task.description || "No description"}</p>
              <small>Due date: {task.dueDate}</small>
              <span
                className={`${styles.status} ${styles[task.status.toLowerCase()]}`}
              >
                {task.status}
              </span>
            </div>

            <div className={styles.cardActions}>
              <button
                className={styles.editBtn}
                onClick={() => handleEdit(task.id)}
              >
                ✏️
              </button>
              <button
                className={styles.deleteBtn}
                onClick={() => handleDelete(task.id)}
              >
                🗑️
              </button>
            </div>
          </li>
        ))}
      </ul>

      {totalPages > 1 && (
        <div className={styles.pagination}>
          <button onClick={handlePrevPage} disabled={page === 0}>
            ← Prev
          </button>
          <span>
            Page {page + 1} of {totalPages}
          </span>
          <button onClick={handleNextPage} disabled={page + 1 >= totalPages}>
            Next →
          </button>
        </div>
      )}
    </div>
  );
}

export default TaskList;
