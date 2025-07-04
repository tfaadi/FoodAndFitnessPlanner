package com.planner.repository;

import com.planner.entity.Workout;
import com.planner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserOrderByTimestampDesc(User user);
    List<Workout> findByUser(User user);
}
