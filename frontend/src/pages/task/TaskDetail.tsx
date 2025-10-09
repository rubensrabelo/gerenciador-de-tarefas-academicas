import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";


import styles from "./TaskDetail.module.css";

import type { TaskResponse } from "../../api/models/interface/task/task-response.interface";
import { TaskError } from "../../api/services/errors/task.error";
import { getById } from "../../api/services/task/task-find-by-id.service";

function TaskDetail() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();

    const [task, setTask] = useState<TaskResponse | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

    async function fetchTask() {
        if (!id) return;
        try {
            setLoading(true);
            const data = await getById(Number(id));
            setTask(data);
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

    useEffect(() => {
        fetchTask();
    }, [id]);

    return (
        <div className={styles.container}>
            {loading && <p>Loading...</p>}

            {!loading && error && <p className={styles.error}>{error}</p>}

            {!loading && !error && (
                <>
                    {task ? (
                        <>
                            <h1>{task.title}</h1>
                            <p>{task.description || "No description available."}</p>
                            <p>
                                <strong>Due Date:</strong> {task.dueDate}
                            </p>
                            <p>
                                <strong>Status:</strong> {task.status}
                            </p>
                        </>
                    ) : (
                        <p>Task not found.</p>
                    )}

                    <button className={styles.backBtn} onClick={() => navigate("/tasks")}>
                        ← Back to tasks
                    </button>
                </>
            )}
        </div>
    );
}

export default TaskDetail;