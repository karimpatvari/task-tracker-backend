package com.simple.scheduler.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskSummaryResponse {
    private Long completedTaskNumber;
    private Long uncompletedTaskNumber;
}
