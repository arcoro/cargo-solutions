package com.cargo.solutions.domain.ports.in;

import com.cargo.solutions.domain.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);

}
