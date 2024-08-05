package com.cargo.solutions.infraestructure.security;

import com.cargo.solutions.domain.model.Security;
import com.cargo.solutions.domain.ports.in.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserDetailsService {

    private final SecurityService securityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Security security = this.securityService.validatedUserAccount(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
        String role = this.securityService.getRoleByUser(username);
        return UserSecurityDetailsImpl.builder()
                .username(username)
                .password(security.getPassword())
                .isEnabled(security.isEnabled())
                .isAccountNonExpired(true)
                .isAccountNonLocked(!security.isBlocked())
                .isCredentialsNonExpired(security.getPasswordExpiresAt().isAfter(LocalDateTime.now()))
                .isFirstLogin(ObjectUtils.isEmpty(security.getFirstLogin()))
                .authorities(Collections.singleton(new SimpleGrantedAuthority(role)))
                .build();
    }
}
