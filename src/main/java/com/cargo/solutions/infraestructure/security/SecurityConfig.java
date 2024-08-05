package com.cargo.solutions.infraestructure.security;

import com.cargo.solutions.application.service.JwtTokenService;
import com.cargo.solutions.application.service.RoleServiceImpl;
import com.cargo.solutions.application.service.SecurityServiceImpl;
import com.cargo.solutions.domain.ports.in.SecurityService;
import com.cargo.solutions.domain.ports.in.UserService;
import com.cargo.solutions.domain.ports.out.PasswordEncoderService;
import com.cargo.solutions.domain.ports.out.RoleRepository;
import com.cargo.solutions.domain.ports.out.SecurityRepository;
import com.cargo.solutions.infraestructure.repository.JpaSecurityRepository;
import com.cargo.solutions.infraestructure.repository.imp.SecurityRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.tokenValidity}")
    private long tokenValidity;

    private final JpaSecurityRepository jpaSecurityRepository;

    private final RoleRepository roleRepository;

    private final UserService userService;

    @Bean
    public SecurityRepository securityRepository() {
        return new SecurityRepositoryImpl(jpaSecurityRepository);
    }

    @Bean
    public JwtTokenService jwtTokenGenerator() {
        return new JwtTokenService(secret, tokenValidity);
    }

    @Bean
    public RoleServiceImpl roleServiceImpl() {
        return new RoleServiceImpl(roleRepository);
    }

    @Bean
    public SecurityService securityService() {
        return new SecurityServiceImpl(userService, securityRepository(), jwtTokenGenerator(), passwordEncoderImpl(), roleServiceImpl());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserSecurityServiceImpl(securityService());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers("/auth/login")
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoderService passwordEncoderImpl() {
        return new PasswordEncoderServiceImp();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
