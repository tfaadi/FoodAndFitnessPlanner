package com.planner.repository;

import com.planner.entity.Goal;
import com.planner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByUser(User user);
    List<Goal> findByUserOrderByTimestampDesc(User user);
}

