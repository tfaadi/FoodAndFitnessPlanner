package com.planner.controller;

import com.planner.entity.*;
import com.planner.repository.ReplyRepository;
import com.planner.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private MealService mealService;
    @Autowired private WorkoutService workoutService;
    @Autowired private GoalService goalService;
    @Autowired private QueryService queryService;
    @Autowired private ReplyRepository replyRepository;
    @Autowired private UserService userService;
    //@Autowired private ChartService chartService;

    @GetMapping("/dashboard")
    public String userDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        model.addAttribute("meals", mealService.getMealsByUser(user));
        model.addAttribute("workouts", workoutService.getWorkoutsByUser(user));

        return "user/dashboard";
    }

    @GetMapping("/record-meal")
    public String recordMealForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        model.addAttribute("meal", new Meal());
        return "user/record_meal";
    }

    @PostMapping("/record-meal")
    public String recordMeal(@ModelAttribute Meal meal, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        meal.setUser(user);
        mealService.saveMeal(meal);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/record-workout")
    public String recordWorkoutForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        model.addAttribute("workout", new Workout());
        return "user/record_workout";
    }

    @PostMapping("/record-workout")
    public String recordWorkout(@ModelAttribute Workout workout, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        workout.setUser(user);
        workoutService.saveWorkout(workout);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/set-goals")
    public String setGoalForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        model.addAttribute("goal", new Goal());
        return "user/set_goals";
    }

    @PostMapping("/set-goals")
    public String saveGoals(@ModelAttribute Goal goal, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        goal.setUser(user);
        goalService.saveGoal(goal);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/progress")
    public String viewProgress(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Goal> allGoals = goalService.getGoalsByUser(user);
        List<Goal> pendingGoals = allGoals.stream().filter(g -> !g.isAchieved()).toList();
        List<Goal> completedGoals = allGoals.stream().filter(Goal::isAchieved).toList();

        model.addAttribute("pendingGoals", pendingGoals);
        model.addAttribute("completedGoals", completedGoals);

        // 🗓 Simplified calendar data
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        List<LocalDate> workoutDates = workoutService.getWorkoutDatesByUserAndMonth(user, year, month);
        Set<Integer> workoutDays = workoutDates.stream().map(LocalDate::getDayOfMonth).collect(Collectors.toSet());

        model.addAttribute("workoutDays", workoutDays); // Just the days
        model.addAttribute("year", year);
        model.addAttribute("month", month);

        return "user/progress";
    }



    // Toggle completion
    @PostMapping("/toggle-goal/{id}")
    public String toggleGoal(@PathVariable Long id) {
        Goal goal = goalService.findById(id);
        if (goal != null) {
            goal.setAchieved(!goal.isAchieved());
            goalService.updateGoal(goal);
        }
        return "redirect:/user/progress";
    }

    // Delete goal
    @GetMapping("/deleteGoal/{id}")
    public String deleteGoal(@PathVariable Long id) {
        goalService.deleteGoalById(id);
        return "redirect:/user/progress";
    }

    // Edit goal form
    @GetMapping("/editGoal/{id}")
    public String editGoalForm(@PathVariable Long id, Model model) {
        Goal goal = goalService.findById(id);
        model.addAttribute("goal", goal);
        return "user/edit_goal";
    }

    // Update goal
    @PostMapping("/updateGoal")
    public String updateGoal(@ModelAttribute Goal goal, HttpSession session) {
        User user = (User) session.getAttribute("user");
        goal.setUser(user);
        goalService.updateGoal(goal);
        return "redirect:/user/progress";
    }

//    @GetMapping("/progress-chart")
//    @ResponseBody
//    public byte[] showProgressChart(HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        return chartService.generateGoalConsistencyChart(user);
//    }
    
    @GetMapping("/ask-query")
    public String askQueryForm(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "redirect:/login";
        model.addAttribute("query", new Query());
        return "user/ask_query";
    }

    @PostMapping("/ask-query")
    public String postQuery(@ModelAttribute Query query, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        queryService.postQuery(query.getContent(), user);
        return "redirect:/user/view-queries";
    }

    @GetMapping("/view-queries")
    public String viewQueries(Model model) {
        model.addAttribute("queries", queryService.getAllQueries());
        return "user/view_queries";
    }

    @GetMapping("/query/{queryId}/replies")
    public String viewReplies(@PathVariable String queryId, Model model) {
        model.addAttribute("replies", replyRepository.findByQuery_QueryId(queryId));
        return "user/view_replies";
    }

    @GetMapping("/delete-account")
    public String deleteAccount(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userService.deleteUser(user);
            session.invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/editMeal/{id}")
    public String editMealForm(@PathVariable Long id, Model model) {
        Meal meal = mealService.findById(id);
        model.addAttribute("meal", meal);
        return "user/edit_meal";
    }

    @PostMapping("/updateMeal")
    public String updateMeal(@ModelAttribute Meal meal, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            meal.setUser(user); // reattach user in case it's not passed from form
            mealService.updateMeal(meal);
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/deleteMeal/{id}")
    public String deleteMeal(@PathVariable Long id) {
        mealService.deleteMealById(id);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/editWorkout/{id}")
    public String editWorkoutForm(@PathVariable Long id, Model model) {
        Workout workout = workoutService.findById(id);
        model.addAttribute("workout", workout);
        return "user/edit_workout";
    }

    @PostMapping("/updateWorkout")
    public String updateWorkout(@ModelAttribute Workout workout, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            workout.setUser(user); // reattach user
            workoutService.updateWorkout(workout);
        }
        return "redirect:/user/dashboard";
    }

    @GetMapping("/deleteWorkout/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkoutById(id);
        return "redirect:/user/dashboard";
    }
}
