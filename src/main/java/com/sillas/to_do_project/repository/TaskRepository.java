package com.sillas.to_do_project.repository;
import com.sillas.to_do_project.entities.Task;
import com.sillas.to_do_project.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    Page<Task> findByUser(User user, Pageable pageable);
}
