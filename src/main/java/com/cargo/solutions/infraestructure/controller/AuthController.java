package com.cargo.solutions.infraestructure.controller;

import com.cargo.solutions.application.service.SecurityServiceImpl;
import com.cargo.solutions.domain.ctes.Constants;
import com.cargo.solutions.domain.dto.Response;
import com.cargo.solutions.domain.dto.UserCredentials;
import com.cargo.solutions.domain.dto.LoginResponse;
import com.cargo.solutions.domain.dto.ChangePasswordRequest;
import com.cargo.solutions.infraestructure.security.UserAuthenticateServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserAuthenticateServiceImpl authenticationUser;

    private final SecurityServiceImpl securityService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserCredentials request) {
        LoginResponse loginResponse = this.authenticationUser.authenticateUser(request);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Response> changePasswordFirstLogin(@RequestBody ChangePasswordRequest request) {
        securityService.updatePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok(Response.builder().code(Constants.CODE_SUCCESS).message("Password changed successfully").build());
    }

}
