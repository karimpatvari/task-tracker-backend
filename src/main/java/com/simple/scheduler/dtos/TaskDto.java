package com.simple.scheduler.dtos;

import com.simple.scheduler.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {

    private Integer id;
    private String title;
    private String text;
    private UserDto user;
    private Boolean isCompleted;
    private Timestamp completedAt;

}
