package com.chappi3.QRGen.service;

import com.chappi3.QRGen.model.ERole;
import com.chappi3.QRGen.model.Role;
import com.chappi3.QRGen.model.User;
import com.chappi3.QRGen.repository.RoleRepository;
import com.chappi3.QRGen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.chappi3.QRGen.model.ERole.ROLE_ADMIN;
import static com.chappi3.QRGen.model.ERole.ROLE_USER;

@Service
public class StartupService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StartupService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void populateDbIfNeeded() {
        final Role adminRole = createRoleIfNotFound(ROLE_ADMIN);
        final Role userRole = createRoleIfNotFound(ROLE_USER);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
//        Set<Role> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        createUserIfNotFound("user", "user@qrgen.com", "password", userRoles);
        createUserIfNotFound("admin", "admin@qrgen.com", "password", adminRoles);
    }

    private Role createRoleIfNotFound(final ERole name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        Role role;
        if (roleOptional.isPresent()) {
            role = roleOptional.get();
        } else {
            role = new Role(name);
            System.out.println("Created role: " + role.getName());
        }
        return roleRepository.save(role);
    }

    private void createUserIfNotFound(String username, String email, String password, Set<Role> roles) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            user = new User(username, email, passwordEncoder.encode(password));
            user.setRoles(roles);
            System.out.println("Created user: " + user.getUsername());
        }
        userRepository.save(user);
    }
}
