package com.planner.service;

import com.planner.entity.User;
import com.planner.entity.Workout;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutService {
    void saveWorkout(Workout workout);
    List<Workout> getWorkoutsByUser(User user);
    Workout findById(Long id);
    void updateWorkout(Workout workout);
    void deleteWorkoutById(Long id);
    List<LocalDate> getWorkoutDatesByUserAndMonth(User user, int year, int month);

}

