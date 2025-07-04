package com.planner.service;

import com.planner.entity.Meal;
import com.planner.entity.User;

import java.util.List;

public interface MealService {
    void saveMeal(Meal meal);
    List<Meal> getMealsByUser(User user);
    Meal findById(Long id);
    void updateMeal(Meal meal);
    void deleteMealById(Long id);

}

