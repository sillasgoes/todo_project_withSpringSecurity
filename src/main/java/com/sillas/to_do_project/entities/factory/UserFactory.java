package com.sillas.to_do_project.entities.factory;

import com.sillas.to_do_project.entities.Role;
import com.sillas.to_do_project.entities.User;

import java.util.Set;

public interface UserFactory {
    User create(String username, String password, Set<Role> role);
}
