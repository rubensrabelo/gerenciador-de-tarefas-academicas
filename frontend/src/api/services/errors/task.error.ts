export class TaskError extends Error {
    status: number;

    constructor(message: string, status: number) {
        super(message);
        this.name = "TaskError";
        this.status = status;
    }
}