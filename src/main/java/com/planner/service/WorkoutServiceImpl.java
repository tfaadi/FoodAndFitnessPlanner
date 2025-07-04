package com.planner.service;

import com.planner.entity.User;
import com.planner.entity.Workout;
import com.planner.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Override
    public void saveWorkout(Workout workout) {
        workoutRepository.save(workout);
    }

    @Override
    public List<Workout> getWorkoutsByUser(User user) {
        return workoutRepository.findByUserOrderByTimestampDesc(user);
    }
    
    @Override
    public Workout findById(Long id) {
        return workoutRepository.findById(id).orElse(null);
    }

    @Override
    public void updateWorkout(Workout updatedWorkout) {
        Workout existingWorkout = workoutRepository.findById(updatedWorkout.getId()).orElse(null);
        if (existingWorkout != null) {
            existingWorkout.setWorkoutType(updatedWorkout.getWorkoutType());
            existingWorkout.setDurationInMinutes(updatedWorkout.getDurationInMinutes());
            workoutRepository.save(existingWorkout);
        }
    }


    @Override
    public void deleteWorkoutById(Long id) {
        workoutRepository.deleteById(id);
    }
    
    @Override
    public List<LocalDate> getWorkoutDatesByUserAndMonth(User user, int year, int month) {
        return workoutRepository.findByUser(user).stream()
                .map(w -> w.getTimestamp().toLocalDate())
                .filter(date -> date.getYear() == year && date.getMonthValue() == month)
                .distinct()
                .toList();
    }

}

