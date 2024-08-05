package com.cargo.solutions.application.service;

import com.cargo.solutions.domain.ctes.Constants;
import com.cargo.solutions.domain.dto.LoginResponse;
import com.cargo.solutions.domain.exception.InvalidPasswordException;
import com.cargo.solutions.domain.exception.UnprocessableEntityException;
import com.cargo.solutions.domain.model.Security;
import com.cargo.solutions.domain.model.User;
import com.cargo.solutions.domain.ports.in.SecurityService;
import com.cargo.solutions.domain.ports.in.UserService;
import com.cargo.solutions.domain.ports.out.PasswordEncoderService;
import com.cargo.solutions.domain.ports.out.SecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;

    private final SecurityRepository securityRepository;

    private final JwtTokenService jwtTokenService;

    private final PasswordEncoderService passwordEncoder;

    private final RoleServiceImpl roleServiceImpl;

    @Override
    public Optional<Security> validatedUserAccount(String username) {
        Optional<Security> optionalSecurity = getSecurityByUsername(username);
        if (optionalSecurity.isPresent()) {
            if (!optionalSecurity.get().isValidated()) {
                throw new UnprocessableEntityException("Unverified Account");
            }

            if (!optionalSecurity.get().isEnabled()) {
                throw new UnprocessableEntityException("Account Inactive");
            }

            if (optionalSecurity.get().isBlocked()) {
                throw new UnprocessableEntityException("Blocked Account");
            }

            return optionalSecurity;
        }
        throw new UnprocessableEntityException("Invalid credentials");
    }

    @Override
    public LoginResponse generateToken(List<String> list, String username) {
        LoginResponse loginResponse = new LoginResponse();
        Optional<Security> optionalSecurity = getSecurityByUsername(username);
        if (optionalSecurity.isPresent()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("role", getRoleByUser(username));
            loginResponse.setToken(jwtTokenService.generateToken(claims, username));
            loginResponse.setCode(Constants.CODE_SUCCESS);
            if (ObjectUtils.isEmpty(optionalSecurity.get().getFirstLogin())) {
                loginResponse.setCode(Constants.CODE_FIRST_LOGIN);
            }
            return loginResponse;
        }
        throw new UnprocessableEntityException("Invalid credentials");
    }

    @Override
    public String getRoleByUser(String username) {
        String role = roleServiceImpl.getRoleByUser(username);
        if (ObjectUtils.isEmpty(role)) throw new UnprocessableEntityException("Invalid role");
        return role;
    }

    public void updatePassword(String username, String oldPassword, String newPassword) {
        Optional<Security> optionalSecurity = getSecurityByUsername(username);
        if (optionalSecurity.isPresent()) {
            Security security = optionalSecurity.get();
            if (!passwordEncoder.matches(oldPassword, security.getPassword())) {
                throw new InvalidPasswordException("Old password is not correct");
            }
            validatePassword(newPassword);
            String encryptedPassword = passwordEncoder.encode(newPassword);
            security.setPassword(encryptedPassword);
            addToPasswordHistory(security, encryptedPassword);
            if (ObjectUtils.isEmpty(security.getFirstLogin())) security.setFirstLogin(LocalDateTime.now());
            securityRepository.save(security);
            return;
        }
        throw new UnprocessableEntityException("Invalid credentials");

    }

    private void validatePassword(String newPassword) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*().]).*$";
        if (!newPassword.matches(regex)) {
            throw new InvalidPasswordException("New password does not meet the requirements");
        }
    }

    private Optional<Security> getSecurityByUsername(String username) {
        Optional<User> userOptional = userService.findByEmail(username);
        if (userOptional.isPresent()) {
            return Optional.ofNullable(securityRepository.findByUserId(userOptional.get().getId()).orElseThrow(() -> new UnprocessableEntityException("Invalid credentials")));
        }
        throw new UnprocessableEntityException("Invalid credentials");
    }


    private void addToPasswordHistory(Security security, String newPassword) {
        if (ObjectUtils.isEmpty(security.getHistoryPasswords())) {
            Map<String, Object> map = new HashMap<>();
            map.put("1", newPassword);
            security.setHistoryPasswords(map);
            return;
        }
        int nextIndex = security.getHistoryPasswords().size() + 1;
        security.getHistoryPasswords().put(String.valueOf(nextIndex), newPassword);
    }


}
