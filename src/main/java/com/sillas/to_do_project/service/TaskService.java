package com.sillas.to_do_project.service;

import com.sillas.to_do_project.controller.dto.*;
import com.sillas.to_do_project.entities.Task;
import com.sillas.to_do_project.repository.TaskRepository;
import com.sillas.to_do_project.repository.UserRepository;
import com.sillas.to_do_project.service.exception.TaskIsEmptyException;
import com.sillas.to_do_project.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private static final Logger LOGGER =  LoggerFactory.getLogger(TaskService.class);


    public void addTask(String content, UUID user){
        LOGGER.info("Adding task{}User: {}", content, user);
        var userT = userRepository.findById(user)
                .orElseThrow(() -> new UserNotFoundException(user.toString()));

        if(content.isEmpty()){
            throw new TaskIsEmptyException();
        }

        Task newTask = new Task();
        newTask.setContent(content);
        newTask.setUser(userT);
        newTask.setTaskStatus(Task.Status.OPEN);

        taskRepository.save(newTask);
        LOGGER.info("Task added: {}", newTask);
    }

    public List<TaskDto> listTaskUser(UUID user_id) {
        LOGGER.info("Listing tasks by user: {}", user_id);
        var dbUser = userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException(user_id.toString()));

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
        LOGGER.info("Listing all tasks");
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
        LOGGER.info("Feeding tasks by user: {}", user_id);
        var userDb = userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException(user_id.toString()));
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
