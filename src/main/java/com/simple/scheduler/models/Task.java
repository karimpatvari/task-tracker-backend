package com.simple.scheduler.models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", columnDefinition = "TEXT", nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted;

    @Column(name = "completed_at", nullable = true)
    private Timestamp completedAt;

    public void CompleteTask(){
        this.isCompleted = true;
        this.completedAt = new Timestamp(System.currentTimeMillis());
    }

    public void UnCompleteTask(){
        this.isCompleted = false;
        this.completedAt = new Timestamp(System.currentTimeMillis());
    }
}
