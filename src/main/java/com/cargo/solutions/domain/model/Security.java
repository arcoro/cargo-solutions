package com.cargo.solutions.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Security {

    private UUID id;
    private UUID userId;
    private String password;
    private LocalDateTime firstLogin;
    private LocalDateTime lastLogin;
    private Integer intents;
    private Map<String,Object> historyPasswords;
    private boolean validated;
    private boolean enabled;
    private boolean blocked;
    private LocalDateTime passwordExpiresAt;
    private String verificationCode;
    private String recoveryCode;
    private String otpCode;

}
