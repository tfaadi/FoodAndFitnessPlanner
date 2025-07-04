package com.planner.service;

import com.planner.entity.Goal;
import com.planner.entity.User;
import com.planner.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public void saveGoal(Goal goal) {
        goalRepository.save(goal);
    }

    @Override
    public List<Goal> getGoalsByUser(User user) {
        return goalRepository.findByUserOrderByTimestampDesc(user);
    }
    
    @Override
    public Goal findById(Long id) {
        return goalRepository.findById(id).orElse(null);
    }

    @Override
    public void updateGoal(Goal goal) {
        goalRepository.save(goal);
    }

    @Override
    public void deleteGoalById(Long id) {
        goalRepository.deleteById(id);
    }

}

