package com.sillas.to_do_project.controller;


import com.sillas.to_do_project.controller.dto.NewUserDto;
import com.sillas.to_do_project.entities.User;
import com.sillas.to_do_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/newuser")
    ResponseEntity<Void> newUser(@RequestBody @NonNull NewUserDto newUser) {
        System.out.println("Chegou aqui "+ newUser);
        userService.newUser(newUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> findUsers(){
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

}
