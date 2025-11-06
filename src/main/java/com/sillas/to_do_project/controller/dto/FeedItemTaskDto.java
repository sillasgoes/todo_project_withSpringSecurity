package com.sillas.to_do_project.controller.dto;

import com.sillas.to_do_project.entities.Task;

import java.time.Instant;

public record FeedItemTaskDto(String Content,
                              Task.Status status,
                              Instant creationTimestamp) {
}
