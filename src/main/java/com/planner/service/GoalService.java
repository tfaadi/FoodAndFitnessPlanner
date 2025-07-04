package com.planner.service;

import com.planner.entity.Goal;
import com.planner.entity.User;

import java.util.List;

public interface GoalService {
    void saveGoal(Goal goal);
    List<Goal> getGoalsByUser(User user);
    Goal findById(Long id);
    void updateGoal(Goal goal);
    void deleteGoalById(Long id);

}
