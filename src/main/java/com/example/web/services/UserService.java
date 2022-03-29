package com.example.web.services;

import com.example.web.conf.ent.Role;
import com.example.web.conf.ent.User;
import com.example.web.repos.RoleRepository;
import com.example.web.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private BCryptPasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;
    final UserDetailsService userDetailsService;


    public User userByName(String username) {
        return userRepository.findUserByUsername(username);
    }

    public void save(User user) {
        Collection<Role> roles = List.of(roleRepository.findRoleByRole("ROLE_USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}

