package com.planner.service;

import com.planner.entity.*;
import com.planner.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final MealRepository mealRepository;
    private final WorkoutRepository workoutRepository;
    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final QueryRepository queryRepository;

    @Autowired
    public UserServiceImpl(
            MealRepository mealRepository,
            WorkoutRepository workoutRepository,
            GoalRepository goalRepository,
            UserRepository userRepository,
            QueryRepository queryRepository) {
        this.mealRepository = mealRepository;
        this.workoutRepository = workoutRepository;
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    public void saveMeal(Meal meal, User user) {
        meal.setUser(user);
        mealRepository.save(meal);
    }

    @Override
    public void saveWorkout(Workout workout, User user) {
        workout.setUser(user);
        workoutRepository.save(workout);
    }

    @Override
    public void saveGoal(Goal goal, User user) {
        goal.setUser(user);
        goalRepository.save(goal);
    }

    @Override
    public List<Meal> getUserMeals(User user) {
        return mealRepository.findByUserOrderByTimestampDesc(user);
    }

    @Override
    public List<Workout> getUserWorkouts(User user) {
        return workoutRepository.findByUserOrderByTimestampDesc(user);
    }

    @Override
    public List<Goal> getUserGoals(User user) {
        return goalRepository.findByUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<Query> getUserQueries(User user) {
        return user.getQueries(); // or queryRepository.findByUser(user);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
        }
    }


}
