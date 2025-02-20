package com.simple.scheduler.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UpdateTaskRequest {
    private Long taskId;
    private String title;
    private String text;
}
