package com.sillas.to_do_project.controller;

import com.sillas.to_do_project.controller.dto.LoginRequest;
import com.sillas.to_do_project.controller.dto.LoginResponse;
import com.sillas.to_do_project.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws BadRequestException {
        var loginResponse = tokenService.token(request);
        return ResponseEntity.ok(loginResponse);
    }

}
