import ENV from "../../../config/env.config";
import type { TaskResponse } from "../../models/interface/task/task-response.interface";
import { parseErrorResponse } from "../../utils/parse-error-response";
import { TaskError } from "../errors/task.error";

interface PaginatedResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}

interface GetAllTasksParams {
    page?: number;
    size?: number;
    direction?: string;
}

export async function getAll({
    page = 0,
    size = 10,
    direction = "desc",
}: GetAllTasksParams = {}): Promise<PaginatedResponse<TaskResponse>> {
    const response = await fetch(
        `${ENV.API_BASE_URL}/tasks?page=${page}&size=${size}&sort=${direction}`,
        {
            method: "GET",
            headers: { "Content-Type": "application/json" },
        }
    );

    if (!response.ok) {
        const { message, status } = await parseErrorResponse(response);
        throw new TaskError(message, status);
    }

    return response.json();
}