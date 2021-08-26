package com.github.braians.springblog.service;

import javax.transaction.TransactionScoped;

import com.github.braians.springblog.exception.ResourceAlreadyExistException;
import com.github.braians.springblog.exception.ResourceNotFoundException;
import com.github.braians.springblog.model.Role;
import com.github.braians.springblog.model.RoleName;
import com.github.braians.springblog.model.User;
import com.github.braians.springblog.payload.JwtAuthenticationResponse;
import com.github.braians.springblog.payload.LoginRequest;
import com.github.braians.springblog.payload.SignInRequest;
import com.github.braians.springblog.repository.RoleRepository;
import com.github.braians.springblog.repository.UserRepository;
import com.github.braians.springblog.security.JwtTokenProvider;
import com.github.braians.springblog.security.UserPrincipal;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
            AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(SignInRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ResourceAlreadyExistException("User", "username", userRequest.getUsername());
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ResourceAlreadyExistException("User", "email", userRequest.getEmail());
        }

        User user = new User();
        user.addUser(userRequest.getName(), userRequest.getUsername(), userRequest.getPassword(),
                userRequest.getEmail());

        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role", "USER"));
        user.addRole(role);
        return userRepository.save(user);
    }

    @TransactionScoped
    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userLogged = (UserPrincipal) authentication.getPrincipal();

        String jwt = jwtTokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt, userLogged.getUsername(), userLogged.getAuthorities());
    }
}
