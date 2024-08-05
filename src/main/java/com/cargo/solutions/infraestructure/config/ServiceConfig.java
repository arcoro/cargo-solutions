package com.cargo.solutions.infraestructure.config;

import com.cargo.solutions.application.service.*;
import com.cargo.solutions.domain.ports.in.*;
import com.cargo.solutions.domain.ports.out.*;
import com.cargo.solutions.infraestructure.repository.*;
import com.cargo.solutions.infraestructure.repository.imp.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {

    private final ObjectMapper objectMapper;

    private final JpaUserRepository jpaUserRepository;

    private final JpaCompanyRepository jpaCompanyRepository;

    private final JpaRoleRepository jpaRoleRepository;

    private final JpaPageRepository jpaPageRepository;

    private final JpaPageRoleRepository jpaPageRoleRepository;

    private final CacheRepository cacheRepository;

    @Bean
    public CacheService cacheService() {
        return new CacheServiceImpl(cacheRepository);
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl(jpaUserRepository);
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository(), cacheService(), objectMapper);
    }

    @Bean
    public CompanyRepository companyRepository() {
        return new CompanyRepositoryImpl(jpaCompanyRepository);
    }

    @Bean
    public CompanyService companyService() {
        return new CompanyServiceImpl(companyRepository());
    }

    @Bean
    public RoleRepository roleRepository() {
        return new RoleRepositoryImpl(jpaRoleRepository);
    }

    @Bean
    public RoleService roleService() {
        return new RoleServiceImpl(roleRepository());
    }

    @Bean
    public PageRepository pageRepository() {
        return new PageRepositoryImpl(jpaPageRepository);
    }

    @Bean
    public PageService pageService() {
        return new PageServiceImpl(pageRepository());
    }

    @Bean
    public PageRoleRepository pageRoleRepository() {
        return new PageRoleRepositoryImpl(jpaPageRoleRepository);
    }

    @Bean
    public PageRoleService pageRoleService() {
        return new PageRoleServiceImpl(pageRoleRepository(), pageService(), cacheService(), objectMapper);
    }

}
