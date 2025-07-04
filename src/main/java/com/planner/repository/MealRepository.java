package com.planner.repository;

import com.planner.entity.Meal;
import com.planner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
	List<Meal> findByUserOrderByTimestampDesc(User user);
}

