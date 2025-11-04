package com.sillas.to_do_project.config;

import com.sillas.to_do_project.entities.Role;
import com.sillas.to_do_project.entities.User;
import com.sillas.to_do_project.repository.UserRepository;
import com.sillas.to_do_project.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
@AllArgsConstructor
@RequiredArgsConstructor
public class ObjectsInitializerConfig implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByName("admin");
        var basicUser = userRepository.findByName("basic");
        var role = roleRepository.findByName(Role.Values.ADMIN.name());

        userAdmin.ifPresentOrElse(
                (user) -> System.out.println("Usuário admin já existe"),
                () -> {
                    var user = new User();
                     user.setUsername("admin");
                     user.setPassword(passwordEncoder.encode("123"));
                     user.setRole(Set.of(role));
                     userRepository.save(user);
                }
        );


    }
}
