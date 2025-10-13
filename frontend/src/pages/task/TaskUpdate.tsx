import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import styles from "./TaskUpdate.module.css";

import type { TaskCreate } from "../../api/models/interface/task/task-create.interface";
import { getById } from "../../api/services/task/task-find-by-id.service";
import { update } from "../../api/services/task/task-update.service";
import { TaskError } from "../../api/services/errors/task.error";

function TaskUpdate() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [formData, setFormData] = useState<TaskCreate>({
    title: "",
    description: "",
    dueDate: "",
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function fetchTask() {
      if (!id) return;
      try {
        setLoading(true);
        const task = await getById(Number(id));
        setFormData({
          title: task.title,
          description: task.description || "",
          dueDate: task.dueDate || "",
        });
      } catch (err: any) {
        if (err instanceof TaskError) {
          setError(err.message);
        } else {
          setError("Failed to load task details.");
        }
      } finally {
        setLoading(false);
      }
    }
    fetchTask();
  }, [id]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!id) return;
    setLoading(true);
    setError(null);

    try {
      await update(Number(id), formData);
      navigate("/tasks");
    } catch (err: any) {
      if (err instanceof TaskError) {
        setError(err.message);
      } else {
        setError("Unexpected error updating task.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className={styles.container}>
      <h1>Update Task</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title</label>
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            required
          />
        </div>

        <div>
          <label>Description</label>
          <textarea
            name="description"
            value={formData.description}
            onChange={handleChange}
          />
        </div>

        <div>
          <label>Due Date</label>
          <input
            type="datetime-local"
            name="dueDate"
            value={formData.dueDate}
            onChange={handleChange}
            required
          />
        </div>

        {error && <p className={styles.error}>{error}</p>}

        <button type="submit" disabled={loading}>
          {loading ? "Updating..." : "Update Task"}
        </button>
      </form>
    </div>
  );
}

export default TaskUpdate;
