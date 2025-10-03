package io.github.rubensrabelo.ms.task.repository;

import io.github.rubensrabelo.ms.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
