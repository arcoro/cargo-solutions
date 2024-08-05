package com.cargo.solutions.domain.ports.out;

public interface PasswordEncoderService {
    String encode(String rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
