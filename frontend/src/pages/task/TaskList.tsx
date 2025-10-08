import { useEffect, useState } from "react"

import styles from "./TaskList.module.css"

import type { TaskResponse } from "../../api/models/interface/task/task-response.interface"
import { TaskError } from "../../api/services/errors/task.error";
import { getAll } from "../../api/services/task/task-find-all.service";

function TaskList() {
    const [tasks, setTasks] = useState<TaskResponse[]>([]);
    const [page, setPage] = useState<number>(0);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);

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
        if (page > 0)
            loadTasks(page - 1);
    };

    const handleNextPage = () => {
        if (page + 1 < totalPages)
            loadTasks(page + 1);
    };

    return (
        <>
            <div className={styles.container}>
                <h1>All Tasks</h1>

                {loading && <p>Loading...</p>}
                {error && <p className={styles.error}>{error}</p>}

                {!loading && tasks.length === 0 && <p>No tasks found.</p>}

                <ul className={styles.taskList}>
                    {tasks.map((task) => (
                        <li key={task.id} className={styles.taskCard}>
                            <h3>{task.title}</h3>
                            <p>{task.description || "No description"}</p>
                            <small>Due date: {task.dueDate}</small>
                            <span className={`${styles.status} ${styles[task.status.toLowerCase()]}`}>
                                {task.status}
                            </span>
                        </li>
                    ))}
                </ul>

                <div className={styles.pagination}>
                    <button onClick={handlePrevPage} disabled={page === 0}>
                        ← Previous
                    </button>
                    <span>
                        Page {page + 1} of {totalPages}
                    </span>
                    <button onClick={handleNextPage} disabled={page + 1 >= totalPages}>
                        Next →
                    </button>
                </div>
            </div>
        </>
    );
}

export default TaskList