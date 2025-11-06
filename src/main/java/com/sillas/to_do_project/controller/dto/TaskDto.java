package com.sillas.to_do_project.controller.dto;

import com.sillas.to_do_project.entities.Task;
import com.sillas.to_do_project.entities.User;

import java.time.Instant;

public record TaskDto(Long task_id,
                      UserDto user,
                      String content,
                      Task.Status status,
                      Instant creationTimestamp){
}
