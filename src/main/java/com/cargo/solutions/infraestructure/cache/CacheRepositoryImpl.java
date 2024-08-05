package com.cargo.solutions.infraestructure.cache;

import com.cargo.solutions.domain.ports.out.CacheRepository;
import com.cargo.solutions.infraestructure.util.EncryptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@CommonsLog
public class CacheRepositoryImpl implements CacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    public CacheRepositoryImpl(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void set(String key, Object value) {
        try {
            String encryptedValue = EncryptionUtil.encrypt(serialize(value));
            redisTemplate.opsForValue().set(key, Objects.requireNonNull(encryptedValue));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Optional<Object> get(String key) {
        try {
            String encryptedValue = (String) redisTemplate.opsForValue().get(key);
            if (encryptedValue != null) {
                String decryptedValue = EncryptionUtil.decrypt(encryptedValue);
                Object deserializedValue = deserialize(decryptedValue);
                return Optional.ofNullable(deserializedValue);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Set<String> findKeysByPattern(String pattern) {
        return redisTemplate.keys(pattern);
    }

    private String serialize(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    private Object deserialize(String serializedValue) throws JsonProcessingException {
        return objectMapper.readValue(serializedValue, Object.class);
    }

}
