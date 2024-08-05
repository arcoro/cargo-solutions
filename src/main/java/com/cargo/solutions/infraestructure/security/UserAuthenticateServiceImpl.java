package com.cargo.solutions.infraestructure.security;

import com.cargo.solutions.domain.dto.LoginResponse;
import com.cargo.solutions.domain.dto.UserCredentials;
import com.cargo.solutions.domain.ports.in.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthenticateServiceImpl {

    private final SecurityService securityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse authenticateUser(UserCredentials request) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return this.securityService.generateToken(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),request.getEmail());
    }
}
