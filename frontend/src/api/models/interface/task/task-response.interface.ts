import type { TaskStatus } from "../../enums/task-status.enum";

export interface TaskResponse {
    id: number;
    title: string;
    description?: string;
    dueDate: string;
    status: TaskStatus;
}