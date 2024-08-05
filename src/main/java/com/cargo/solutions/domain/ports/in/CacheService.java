package com.cargo.solutions.domain.ports.in;

import java.util.Optional;
import java.util.Set;

public interface CacheService {

    void set(String key, Object value);

    Optional<Object> get(String key);

    void delete(String key);

    Set<String> findKeysByPattern(String pattern);

}
