package io.github.rubensrabelo.ms.task_overdue_checker.repository;

import io.github.rubensrabelo.ms.task_overdue_checker.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
