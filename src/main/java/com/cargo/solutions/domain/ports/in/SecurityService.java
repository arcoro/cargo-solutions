package com.cargo.solutions.domain.ports.in;

import com.cargo.solutions.domain.dto.LoginResponse;
import com.cargo.solutions.domain.model.Security;

import java.util.List;
import java.util.Optional;

public interface SecurityService {

    Optional<Security> validatedUserAccount(String username);

    LoginResponse generateToken(List<String> list, String username);

    String getRoleByUser(String username);

    void updatePassword(String email, String oldPassword, String newPassword);
}
