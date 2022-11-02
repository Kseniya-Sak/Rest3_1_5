package ru.kata.spring.boot_security.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;

@Component
public class RoleGrantedAuthorityImpl implements GrantedAuthority {
    private Role role;

    public RoleGrantedAuthorityImpl(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getName();
    }
}
