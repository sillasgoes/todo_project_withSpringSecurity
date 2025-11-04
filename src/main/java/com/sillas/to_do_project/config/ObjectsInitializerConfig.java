package com.sillas.to_do_project.config;

import com.sillas.to_do_project.entities.Role;
import com.sillas.to_do_project.entities.User;
import com.sillas.to_do_project.repository.UserRepository;
import com.sillas.to_do_project.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class ObjectsInitializerConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ObjectsInitializerConfig(UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByUsername("admin");
        var basicUser = userRepository.findByUsername("basic");

        System.out.println("chegou aqu:" + userAdmin + basicUser);
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
