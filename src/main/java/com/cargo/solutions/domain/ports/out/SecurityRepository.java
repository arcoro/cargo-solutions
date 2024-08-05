package com.cargo.solutions.domain.ports.out;

import com.cargo.solutions.domain.model.Security;

import java.util.Optional;
import java.util.UUID;

public interface SecurityRepository {

    Optional<Security> findByUserId(UUID userId);
    Security save(Security security);
}
