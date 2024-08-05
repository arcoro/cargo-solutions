package com.cargo.solutions.domain.ports.out;

import com.cargo.solutions.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);
}
