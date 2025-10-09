import ENV from "../../../config/env.config";
import type { TaskResponse } from "../../models/interface/task/task-response.interface";
import type { TaskUpdate } from "../../models/interface/task/task-update.interface";
import { parseErrorResponse } from "../../utils/parse-error-response";
import { TaskError } from "../errors/task.error";

export async function update(
    id: number,
    data: TaskUpdate
): Promise<TaskResponse> {
    const response = await fetch(
        `${ENV.API_BASE_URL}/tasks/${id}`,
        {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        }
    );

    if(!response.ok) {
        const { message, status } = await parseErrorResponse(response);
        throw new TaskError(message, status);
    }

    return response.json();
}