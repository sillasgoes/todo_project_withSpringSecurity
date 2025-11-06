package com.sillas.to_do_project.service;

import com.sillas.to_do_project.controller.dto.*;
import com.sillas.to_do_project.entities.Task;
import com.sillas.to_do_project.repository.TaskRepository;
import com.sillas.to_do_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public void addTask(String content, UUID user){

        var userT = userRepository.findById(user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(content.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        Task newTask = new Task();
        newTask.setContent(content);
        newTask.setUser(userT);
        newTask.setTaskStatus(Task.Status.OPEN);

        taskRepository.save(newTask);
    }

    public List<TaskDto> listTaskUser(UUID user_id) {

        var dbUser = userRepository.findById(user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var task = taskRepository.findByUser(dbUser);

        return task.stream().map(tTask -> {
            return new TaskDto(
              tTask.getTask_id(),
                    new UserDto(
                            tTask.getUser().getUser_id(),
                            tTask.getUser().getUsername(),
                            tTask.getUser().getRole()),
              tTask.getContent(),
              tTask.getTaskStatus(),
              tTask.getCreationTimestamp());
        }).toList();
    }

    public List<TaskDto> listAllTasks() {

        return taskRepository.findAll().stream().map(
                (task) -> {
                    return new TaskDto(
                            task.getTask_id(),
                            new UserDto(
                                    task.getUser().getUser_id(),
                                    task.getUser().getUsername(),
                                    task.getUser().getRole()),
                            task.getContent(),
                            task.getTaskStatus(),
                            task.getCreationTimestamp());
                }).toList();
    }

    public FeedTaskDto feedTask(UUID user_id, int page, int pageSize){

        var userDb = userRepository.findById(user_id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.Direction.DESC,
                "creationTimestamp");

        var feed = taskRepository.findByUser(userDb, pageable)
                .map(user -> new FeedItemTaskDto(
                        user.getContent(),
                        user.getTaskStatus(),
                        user.getCreationTimestamp()));

        var feedTest = taskRepository.findByUser(userDb);

        return new FeedTaskDto(feed.getContent(),
                page,
                pageSize,
                feed.getTotalPages(),
                feed.getTotalElements());
    }
}
