export const TaskStatus = {
    PENDING: "pending",
    IN_PROGRESS: "in_progress",
    COMPLETED: "completed",
    CANCELLED: "cancelled",
    OVERDUE: "overdue",
} as const;

export type TaskStatus = typeof TaskStatus[keyof typeof TaskStatus];