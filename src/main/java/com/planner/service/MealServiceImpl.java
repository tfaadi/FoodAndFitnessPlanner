package com.planner.service;

import com.planner.entity.Meal;
import com.planner.entity.User;
import com.planner.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository mealRepository;

    @Override
    public void saveMeal(Meal meal) {
        mealRepository.save(meal);
    }

    @Override
    public List<Meal> getMealsByUser(User user) {
        return mealRepository.findByUserOrderByTimestampDesc(user);
    }
    
    @Override
    public Meal findById(Long id) {
        return mealRepository.findById(id).orElse(null);
    }

    @Override
    public void updateMeal(Meal updatedMeal) {
        Meal existingMeal = mealRepository.findById(updatedMeal.getId()).orElse(null);
        if (existingMeal != null) {
            existingMeal.setMealType(updatedMeal.getMealType());
            existingMeal.setFoodItems(updatedMeal.getFoodItems());
            mealRepository.save(existingMeal);
        }
    }


    @Override
    public void deleteMealById(Long id) {
        mealRepository.deleteById(id);
    }

}
