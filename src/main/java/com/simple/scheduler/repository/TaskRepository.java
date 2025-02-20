package com.simple.scheduler.repository;

import com.simple.scheduler.models.Task;
import com.simple.scheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByUser(User user);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.isCompleted = true AND t.user.id = :userId")
    long countAllCompletedTasksByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.isCompleted = false AND t.user.id = :userId")
    long getAllUncompletedTasksByUser(@Param("userId") Long userId);

}
