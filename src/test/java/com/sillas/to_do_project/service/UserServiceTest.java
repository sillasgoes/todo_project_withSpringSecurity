package com.sillas.to_do_project.service;

import com.sillas.to_do_project.controller.dto.NewUserDto;
import com.sillas.to_do_project.entities.Role;
import com.sillas.to_do_project.entities.User;
import com.sillas.to_do_project.entities.factory.UserFactory;
import com.sillas.to_do_project.repository.RoleRepository;
import com.sillas.to_do_project.repository.UserRepository;
import com.sillas.to_do_project.service.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder passwordEncoder;
    @Mock
    UserFactory userFactory;

    @InjectMocks
    UserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Captor
    private ArgumentCaptor<String> strReturn;

    /**
     * Estrutura básicas de testes: Mock de todas as dependências externas da classe
     * Injetar dentro da classe a ser testada com @InjectMocks
     * Estrutura de organização dos testes com @Nested na classe a ser testada, dentro dela
     * seus respetivos testes, sempre identificando o nome claramente com o que o teste se
     * proproe: Should Crete User with success
     * Atraves dos testes você perceber falhas de construção do seu código como ex: Acoplação
     * de dependências instânciadas dentro da própria classe: New User. Sempre use depências
     * instanciadas dentro do construtor, isso vai facilitar o mock dessa informação na hora
     * teste
     */

    @Nested
    class newUser {

        @Test
        @DisplayName("Should find a user")
        void shouldCreatedUserWithSuccess(){

            //Arrange: preparar os dados de entrada
            var newDto = new NewUserDto("Sillas", "1234");
            var role = Role.builder().role_id(1L).name("ADMIN").build();
            var role2 = Role.builder().role_id(2L).name("BASIC").build();

            var user = User.builder()
                    .user_id(UUID.randomUUID())
                    .username(newDto.username())
                    .password(newDto.password())
                    .role(Set.of(role,role2))
                    .build();

            doReturn(role).when(roleRepository).findByName("admin");
            doReturn(Optional.of(role2)).when(roleRepository).findById(2L);
            doReturn(Optional.empty()).when(userRepository).findByUsername(newDto.username());
            doReturn(user).when(userFactory).create(any(), any(), anySet());
            doReturn(user).when(userRepository).save(userCaptor.capture());

            //- Act: executar o método

            var result = userService.newUser(newDto);

            //Assert: verificar o resultado
            verify(userRepository, times(1)).save(userCaptor.capture());
            assertEquals(result.username(), userCaptor.getValue().getUsername());
            assertEquals(result.user_id(), userCaptor.getValue().getUser_id());
            assertEquals(result.role(), userCaptor.getValue().getRole());

        }

        @Test
        @DisplayName("Should Throw exception if user not found")
        void shouldThrowExceptionIfUserNotFound(){
            //Arranger
            var str = "sillas";
            doReturn(Optional.empty()).when(userRepository).findByUsername(strReturn.capture());

            //Act -
            //Assert
            assertThrowsExactly(UserNotFoundException.class, () -> {
                userService.findUser(strReturn.capture());
            });

            verify(userRepository, times(1)).findByUsername(any());
        }
    }

    @Nested
    class findUser {

        @Test
        @DisplayName("Should return user by username")
        void shouldReturnUserByUsername() {

            //Arranged
            var username = "Sillas";
            var role = Role.builder().role_id(1L).name("ADMIN").build();

            User user = User.builder().username("Sillas").password("1234").build();

            doReturn(Optional.of(user)).when(userRepository).findByUsername(username);

            //ACT
            var result = userService.findUser(username);
            //Assert
            assertEquals(user.getUser_id(), result.user_id());

        }

    }
}