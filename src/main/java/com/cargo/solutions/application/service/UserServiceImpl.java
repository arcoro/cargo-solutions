package com.cargo.solutions.application.service;

import com.cargo.solutions.domain.model.User;
import com.cargo.solutions.domain.ports.in.CacheService;
import com.cargo.solutions.domain.ports.in.UserService;
import com.cargo.solutions.domain.ports.out.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.util.Optional;

import static com.cargo.solutions.domain.ctes.KeyConstants.USER_CACHE_KEY;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CacheService cacheService;

    private final ObjectMapper objectMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        ;
        Optional<Object> optional = cacheService.get(String.format(USER_CACHE_KEY, email));
        if (optional.isPresent()) return Optional.of(objectMapper.convertValue(optional.get(), User.class));
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            cacheService.set(String.format(USER_CACHE_KEY, email), userOptional.get());
            return userOptional;
        }
        return Optional.empty();
    }
}
