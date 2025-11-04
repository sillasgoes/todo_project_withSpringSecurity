package com.sillas.to_do_project.config;

import com.sillas.to_do_project.entities.User;
import com.sillas.to_do_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
@RequiredArgsConstructor
public class ObjectsInitializerConfig implements CommandLineRunner {

    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        var userAdmin = userRepository.findByName("admin");
        var basicUser = userRepository.findByName("basic");

        userAdmin.ifPresentOrElse(
                (user) -> System.out.println("Usuário admin já existe"),
                () -> {
                    var user = new User();
                     user.set
                }
        );


    }
}
