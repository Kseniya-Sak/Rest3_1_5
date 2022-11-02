package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImpl;



import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> findAll() {
        System.out.println("get allUsers");
        return userRepository.findAll();
    }

    public User findOne(long id) {
        return userRepository.findById(id).orElse(new User());
    }
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(long id, User updatedUser) {
        updatedUser.setId(id);
        userRepository.save(updatedUser);
    }

    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("start loadUserByUsername method");
        User user = findByUsername(username);
        System.out.println("get Optional ");
        if (user == null) {
            new UsernameNotFoundException("User not found");
        }
        System.out.println("get user " + user);
        return new UserDetailsImpl(user);
    }

    public Set<Role> getRolles(long id) {
        return userRepository.findById(id).get().getRoles();
    }
}
