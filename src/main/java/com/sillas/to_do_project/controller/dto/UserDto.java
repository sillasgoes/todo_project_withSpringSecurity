package com.sillas.to_do_project.controller.dto;

import com.sillas.to_do_project.entities.Role;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

public record UserDto (UUID user_id, String username, Set<Role> role) {

}
