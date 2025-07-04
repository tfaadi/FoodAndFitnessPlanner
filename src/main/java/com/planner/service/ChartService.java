//package com.planner.service;
//
//import com.planner.entity.Goal;
//import com.planner.entity.User;
//import com.planner.repository.GoalRepository;
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtils;
//import org.jfree.chart.JFreeChart;
//import org.jfree.data.category.DefaultCategoryDataset;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.awt.*;
//import java.io.ByteArrayOutputStream;
//import java.time.LocalDate;
//import java.time.temporal.IsoFields;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//@Service
//public class ChartService {
//
//    @Autowired
//    private GoalRepository goalRepository;
//
//    public byte[] generateGoalConsistencyChart(User user) {
//        List<Goal> goals = goalRepository.findByUser(user);
//
//        // Group goals by week
//        Map<String, Integer> weeklyData = new TreeMap<>();
//        for (Goal goal : goals) {
//            if (goal.isAchieved()) {
//                LocalDate date = goal.getTimestamp().toLocalDate();
//                String weekKey = "Week " + date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
//                weeklyData.put(weekKey, weeklyData.getOrDefault(weekKey, 0) + 1);
//            }
//        }
//
//        // Dataset
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        for (Map.Entry<String, Integer> entry : weeklyData.entrySet()) {
//            dataset.addValue(entry.getValue(), "Goals Achieved", entry.getKey());
//        }
//
//        // Chart
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Weekly Goal Completion",
//                "Week",
//                "Goals",
//                dataset
//        );
//
//        chart.getTitle().setPaint(Color.DARK_GRAY);
//
//        // Convert to byte[]
//        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
//            ChartUtils.writeChartAsPNG(baos, chart, 640, 400);
//            return baos.toByteArray();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}

