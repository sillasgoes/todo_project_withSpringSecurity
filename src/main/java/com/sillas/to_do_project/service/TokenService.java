package com.sillas.to_do_project.service;


import com.sillas.to_do_project.controller.dto.LoginRequest;
import com.sillas.to_do_project.controller.dto.LoginResponse;
import com.sillas.to_do_project.entities.Role;
import com.sillas.to_do_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginResponse token(LoginRequest request) throws BadRequestException {

        var user = userRepository.findByUsername(request.username());

        if(user.isEmpty() || !user.get().isLoginCorrect(request, passwordEncoder)) {
            throw new BadRequestException("User or Password Invalid");
        }

        var expires = 300L;

        var scope = user
                .get()
                .getRole()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("mybackend")
                .subject(user.get().getUser_id().toString())
                .expiresAt(Instant.now().plusSeconds(expires))
                .issuedAt(Instant.now())
                .claim("scope", scope)
                .build();

        var jwtValues = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValues);
    }
}
