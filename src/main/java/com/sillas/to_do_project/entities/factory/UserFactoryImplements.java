package com.sillas.to_do_project.entities.factory;

import com.sillas.to_do_project.entities.Role;
import com.sillas.to_do_project.entities.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
public class UserFactoryImplements implements UserFactory {

    public User create(String username, String password, Set<Role> role) {
        return new User(UUID.randomUUID(), username, password, role);
    }
}
