package com.planner.controller;

import com.planner.entity.*;
import com.planner.repository.ReplyRepository;
import com.planner.service.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private UserService userService;
    @Autowired private QueryService queryService;
    @Autowired private ReplyRepository replyRepository;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        List<Query> queries = queryService.getAllQueries();
        model.addAttribute("queries", queries);
        return "admin/dashboard";
    }

    @GetMapping("/view-users")
    public String viewUsers(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/view_users";
    }

    @PostMapping("/remove-user/{username}")
    public String removeUser(@PathVariable String username, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        userService.deleteUserByUsername(username);
        return "redirect:/admin/view-users";
    }

    @GetMapping("/queries")
    public String viewQueries(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        List<Query> queries = queryService.getAllQueries();
        model.addAttribute("queries", queries);
        return "admin/view_queries";
    }

    @GetMapping("/query/{queryId}/replies")
    public String viewReplies(@PathVariable String queryId, HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        List<Reply> replies = replyRepository.findByQuery_QueryId(queryId);
        model.addAttribute("replies", replies);
        model.addAttribute("queryId", queryId);
        return "admin/view_replies";
    }

    @GetMapping("/query/{queryId}/reply")
    public String replyForm(@PathVariable String queryId, HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        model.addAttribute("queryId", queryId);
        model.addAttribute("reply", new Reply());
        return "admin/reply_form";
    }

    @PostMapping("/query/{queryId}/reply")
    public String postReply(@PathVariable String queryId,
                            @ModelAttribute Reply reply,
                            HttpSession session) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        Query query = queryService.getQueryByQueryId(queryId);
        if (query != null) {
            reply.setAdmin(admin);
            reply.setQuery(query);
            replyRepository.save(reply);
        }
        return "redirect:/admin/queries";
    }

    @GetMapping("/delete-account")
    public String deleteAccount(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        adminService.deleteAdmin(admin);
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/delete-query/{queryId}")
    public String deleteQuery(@PathVariable String queryId, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("user");
        if (admin == null) return "redirect:/login";

        queryService.deleteQueryById(queryId);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
