package com.sillas.to_do_project.service;


import com.sillas.to_do_project.controller.dto.NewUserDto;
import com.sillas.to_do_project.controller.dto.UserDto;
import com.sillas.to_do_project.entities.Role;
import com.sillas.to_do_project.entities.User;
import com.sillas.to_do_project.repository.RoleRepository;
import com.sillas.to_do_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public void newUser(NewUserDto dto) {

        User newUser = new User();
        var admin = roleRepository.findByName("admin");
        var basic = roleRepository.findById(2L).orElseThrow();

         userRepository.findByUsername(dto.username()).
                 ifPresentOrElse(
                         (u) -> {
                             throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
                         },
                         () -> {
                             newUser.setUsername(dto.username());
                             newUser.setRole(Set.of(admin, basic));
                             newUser.setPassword(passwordEncoder.encode(dto.password()));
                         });

       System.out.println("Chegou dentro do service com os dados" + newUser);
         userRepository.save(newUser);
    }

    public UserDto findUser(String username){

        User user = userRepository.
                findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não cadastrado"));

        UserDto dto = new UserDto(
                user.getUser_id(),
                user.getUsername(),
                user.getRole());

        return dto;
    }
}
