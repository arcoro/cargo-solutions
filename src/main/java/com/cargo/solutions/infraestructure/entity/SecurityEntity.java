package com.cargo.solutions.infraestructure.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Entity
@Table(name = "security")
public class SecurityEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_login", nullable = false)
    private LocalDateTime firstLogin;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "intents", nullable = false)
    private int intents;

    @Column(name = "history_passwords", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> historyPasswords;

    @Column(name = "validated", nullable = false)
    private boolean validated;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "blocked", nullable = false)
    private boolean blocked;

    @Column(name = "password_expires_at")
    private LocalDateTime passwordExpiresAt;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "recovery_code")
    private String recoveryCode;

    @Column(name = "otp_code")
    private String otpCode;
}
