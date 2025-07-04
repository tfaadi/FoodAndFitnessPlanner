package com.planner.service;

import com.planner.entity.*;

import java.util.List;

public interface UserService {

    void saveMeal(Meal meal, User user);

    void saveWorkout(Workout workout, User user);

    void saveGoal(Goal goal, User user);

    List<Meal> getUserMeals(User user);

    List<Workout> getUserWorkouts(User user);

    List<Goal> getUserGoals(User user);

    void deleteUser(User user);

    List<Query> getUserQueries(User user);
    List<User> getAllUsers();
    void deleteUserByUsername(String username);
}

