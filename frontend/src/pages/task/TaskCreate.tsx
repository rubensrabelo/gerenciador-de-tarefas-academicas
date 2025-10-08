import { useState } from "react";

import styles from "./CreateTask.module.css";

import type { TaskCreate } from "../../api/models/interface/task/task-create.interface";
import { create } from "../../api/services/task/task-create.service";
import { TaskError } from "../../api/services/errors/task.error";

function CreateTask() {
    const [formData, setFormData] = useState<TaskCreate>({
        title: "",
        description: "",
        dueDate: "",
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            await create(formData);
            setFormData({ title: "", description: "", dueDate: "" });
        } catch (err: any) {
            if (err instanceof TaskError) {
                setError(err.message);
            } else {
                setError("Unexpected error registering task.");
            }
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className={styles.container}>
            <h1>Create Task</h1>
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
                        type="date"
                        name="dueDate"
                        value={formData.dueDate}
                        onChange={handleChange}
                        required
                    />
                </div>

                {error && <p className={styles.error}>{error}</p>}

                <button type="submit" disabled={loading}>
                    {loading ? "Creating..." : "Create Task"}
                </button>
            </form>
        </div>
    );
}

export default CreateTask;
