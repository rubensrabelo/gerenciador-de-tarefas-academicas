import ENV from "../../../config/env.config";
import type { TaskResponse } from "../../models/interface/task/task-response.interface";
import { parseErrorResponse } from "../../utils/parse-error-response";
import { TaskError } from "../errors/task.error";

export async function getById(id: number): Promise<TaskResponse> {
    const response = await fetch(
        `${ENV.API_BASE_URL}/tasks/${id}`,
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