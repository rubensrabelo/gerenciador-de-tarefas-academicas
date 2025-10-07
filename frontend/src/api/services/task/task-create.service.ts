import ENV from "../../../config/env.config";
import type { TaskCreate } from "../../models/interface/task/task-create.interface";
import type { TaskResponse } from "../../models/interface/task/task-response.interface";
import { parseErrorResponse } from "../../utils/parse-error-response";
import { TaskError } from "../errors/task.error";

export async function create(data: TaskCreate): Promise<TaskResponse> {
    const response = await fetch(
        `${ENV.API_BASE_URL}/tasks`,
        {
            method: "POST",
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