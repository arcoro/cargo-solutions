package com.cargo.solutions.domain.ports.out;

import java.util.Optional;
import java.util.Set;

public interface CacheRepository {

    void set(String key, Object value);

    Optional<Object> get(String key);

    void delete(String key);

    Set<String> findKeysByPattern(String pattern);
}
