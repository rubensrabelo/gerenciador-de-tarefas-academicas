package io.github.rubensrabelo.ms.task_overdue_checker.application.rabbitmq.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class WorkerMetrics {

    private final Counter tasksProcessed;
    private final Counter tasksFailed;

    public WorkerMetrics(MeterRegistry registry) {
        this.tasksProcessed = Counter.builder("worker_tasks_processed_total")
                .description("Total tasks processed by the worker")
                .register(registry);

        this.tasksFailed = Counter.builder("worker_tasks_failed_total")
                .description("Total number of tasks that failed to process")
                .register(registry);
    }

    public void incrementProcessed() {
        tasksProcessed.increment();
    }

    public void incrementFailed() {
        tasksFailed.increment();
    }
}