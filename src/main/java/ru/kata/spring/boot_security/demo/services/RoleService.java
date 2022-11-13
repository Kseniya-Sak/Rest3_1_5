package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepositories;

    @Transactional
    public Set<Role> findAll() {
        return new HashSet<>(roleRepositories.findAll());
    }

    @Transactional
    public Set<Role> getSetRoles(Set<String> roles) {
        return roleRepositories.getSetRoles(roles);
    }

    @Transactional
    public void save(Role role) {
        roleRepositories.save(role);
    }
}
