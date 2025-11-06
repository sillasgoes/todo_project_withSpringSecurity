package com.sillas.to_do_project.controller;

import com.sillas.to_do_project.controller.dto.TaskDto;
import com.sillas.to_do_project.controller.dto.TaskNewDto;
import com.sillas.to_do_project.service.TaskService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/tasks/user")
    public ResponseEntity<List<TaskDto>> taskOfUser(@RequestParam UUID user_id){
        List<TaskDto> dto = taskService.listTaskUser(user_id);
        return ResponseEntity.ok(dto);
    }
}
