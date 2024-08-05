package com.cargo.solutions.infraestructure.repository.imp;

import com.cargo.solutions.domain.model.User;
import com.cargo.solutions.domain.ports.out.UserRepository;
import com.cargo.solutions.infraestructure.entity.UserEntity;
import com.cargo.solutions.infraestructure.repository.JpaUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(this::toDomain);
    }

    private User toDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setCompanyId(entity.getCompanyId());
        user.setRoleId(entity.getRoleId());
        user.setName(entity.getName());
        user.setLastName(entity.getLastName());
        user.setEmail(entity.getEmail());
        user.setPhone(entity.getPhone());
        user.setIdentityTypeId(entity.getIdentityTypeId());
        user.setIdentity(entity.getIdentity());
        return user;
    }
}
