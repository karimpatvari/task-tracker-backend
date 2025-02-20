package com.simple.scheduler.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
