package com.sillas.to_do_project.controller;

import com.sillas.to_do_project.controller.dto.FeedTaskDto;
import com.sillas.to_do_project.controller.dto.TaskDto;
import com.sillas.to_do_project.controller.dto.TaskNewDto;
import com.sillas.to_do_project.entities.Task;
import com.sillas.to_do_project.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/newtask")
    public ResponseEntity<Void> newTask(@RequestBody TaskNewDto task){
        System.out.println("Chegou aqui:" + task);
        taskService.addTask(task.content(), task.user_id());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tasks")
   // @PreAuthorize("hasAuthority('SCOPE_admin)")
    public ResponseEntity<List<TaskDto>> allTaks() {
        return ResponseEntity.ok(taskService.listAllTasks());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<List<TaskDto>> taskOfUser(@PathVariable("id") UUID user_id){
        List<TaskDto> dto = taskService.listTaskUser(user_id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/tasks/page")
    public ResponseEntity<FeedTaskDto> taskPageFormat(@RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                      @RequestParam(value = "user_id") UUID user_id ) {

       var taskDto = taskService.feedTask(user_id, page, pageSize);

       return ResponseEntity.ok(taskDto);
    }
}
