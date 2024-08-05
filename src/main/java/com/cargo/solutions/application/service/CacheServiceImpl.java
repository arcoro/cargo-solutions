package com.cargo.solutions.application.service;

import com.cargo.solutions.domain.ports.in.CacheService;
import com.cargo.solutions.domain.ports.out.CacheRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final CacheRepository redisRepository;

    @Override
    public void set(String key, Object value) {
        redisRepository.set(key.toUpperCase(), value);
    }

    @Override
    public Optional<Object> get(String key) {
        return redisRepository.get(key.toUpperCase());
    }

    @Override
    public void delete(String key) {
        redisRepository.delete(key.toUpperCase());
    }

    @Override
    public Set<String> findKeysByPattern(String pattern) {
        return redisRepository.findKeysByPattern(pattern.toUpperCase());
    }
}
