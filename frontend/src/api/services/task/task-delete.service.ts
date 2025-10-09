import ENV from "../../../config/env.config";
import { parseErrorResponse } from "../../utils/parse-error-response";
import { TaskError } from "../errors/task.error";

export async function deleteTask (
    id: number
): Promise <void> {
    const response = await fetch(
        `${ENV.API_BASE_URL}/tasks/${id}`,
        {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
        }
    );

    if(!response.ok) {
    const { message, status } = await parseErrorResponse(response);
    throw new TaskError(message, status);
}
}