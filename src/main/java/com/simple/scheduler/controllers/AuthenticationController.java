package com.simple.scheduler.controllers;

import com.common.EmailSendingDto;
import com.simple.scheduler.dtos.*;
import com.simple.scheduler.models.User;
import com.simple.scheduler.services.AuthenticationService;
import com.simple.scheduler.services.EmailSendingService;
import com.simple.scheduler.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final EmailSendingService emailSendingService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserService userService, EmailSendingService emailSendingService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.emailSendingService = emailSendingService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        emailSendingService.SendEmail(new EmailSendingDto(
                request.getEmail(),
                "Registration",
                "Hello " + request.getFirstName() + " " + request.getLastName() + ", "
                + "thank you for registration at Tasker application. Now you can access this page with your credentials"
        ));
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationService.register(request))
                .build();
        //todo Так же, в случае успешной регистрации, пользователю отправляется приветственное письмо
        // (непосредственная отправка письма происходит в другом сервисе, детали ниже в разделе Kafka)
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authenticationService.authenticate(request))
                .build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(
            @RequestHeader(name = "Authorization") String token
    ) {
        User userByToken = userService.getUserByToken(token);
        return ResponseEntity.ok().body(
                userService.userToUserDto(userByToken)
        );
    }

}
