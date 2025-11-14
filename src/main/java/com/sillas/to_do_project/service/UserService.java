package com.sillas.to_do_project.service;


import com.sillas.to_do_project.controller.dto.NewUserDto;
import com.sillas.to_do_project.controller.dto.UserDto;
import com.sillas.to_do_project.entities.User;
import com.sillas.to_do_project.entities.factory.UserFactory;
import com.sillas.to_do_project.repository.RoleRepository;
import com.sillas.to_do_project.repository.UserRepository;
import com.sillas.to_do_project.service.exception.UserAlreadyRegisteredException;
import com.sillas.to_do_project.service.exception.UserNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
@Getter
@Setter
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserFactory factory;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class.getName());

    public List<UserDto> allUsers() {
        List<User> user = userRepository.findAll();

        LOGGER.info("Getting all users...{}",  user);
        return user.stream()
                .map(usuario -> new UserDto(usuario.getUser_id(),
                usuario.getUsername(),
                usuario.getRole())).toList();
    }

    public UserDto newUser(NewUserDto dto) {
        LOGGER.info("Creating user: {}", dto);
        var admin = roleRepository.findByName("admin");
        var basic = roleRepository.findById(2L).orElseThrow();

//         userRepository.findByUsername(dto.username()).
//                 ifPresentOrElse(
//                           (u) -> {
//                             LOGGER.info("User already registered {}", dto.username());
//                             throw new UserAlreadyRegisteredException(u.getUsername());
//                         },
//                         () -> {
//                             newUser.setUsername(dto.username());
//                             newUser.setRole(Set.of(admin, basic));
//                             newUser.setPassword(passwordEncoder.encode(dto.password()));
//                             LOGGER.info("A new user is being created. {}", dto.username());
//                         });

        userRepository.findByUsername(dto.username())
                .ifPresent(usuario -> {
            LOGGER.info("User already registered {}", dto.username());
            throw new UserAlreadyRegisteredException(usuario.getUsername());
        });

        var user = factory.create(dto.username(), passwordEncoder.encode(dto.password()), Set.of(admin, basic));

    var result = userRepository.save(user);

        var newUserDto = new UserDto(result.getUser_id(), result.getUsername(), result.getRole());

         LOGGER.info("Created user: {}", dto.username());

         return newUserDto;
    }

    public UserDto findUser(String username){
        LOGGER.info("Finding user by username: {}", username);
            User user = userRepository.
                findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return new UserDto(
                user.getUser_id(),
                user.getUsername(),
                user.getRole());
    }
}
