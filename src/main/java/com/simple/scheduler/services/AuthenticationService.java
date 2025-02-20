package com.simple.scheduler.services;

import com.simple.scheduler.customExceptionsAndHandler.EmailTakenException;
import com.simple.scheduler.dtos.AuthenticationRequest;
import com.simple.scheduler.dtos.RegisterRequest;
import com.simple.scheduler.models.User;
import com.simple.scheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest request) throws EmailTakenException {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .build();
        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new EmailTakenException("This email is already taken");
                });

        userRepository.save(user);
        return jwtService.generateToken(user);
    }

    public String authenticate(AuthenticationRequest request) throws UsernameNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow( () ->
                        new UsernameNotFoundException("User with this email not found")
                );

        return jwtService.generateToken(user);
    }
}
