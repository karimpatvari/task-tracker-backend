package com.simple.scheduler.services;

import com.simple.scheduler.customExceptionsAndHandler.TokenInvalidException;
import com.simple.scheduler.dtos.UserDto;
import com.simple.scheduler.models.User;
import com.simple.scheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User getUserByToken(String token) {
        token = token.substring(7);

        String s = jwtService.extractUsername(token);
        return userRepository.findByEmail(s).orElseThrow(
                () -> new TokenInvalidException("Token is expired or invalid")
        );
    }

    public UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
