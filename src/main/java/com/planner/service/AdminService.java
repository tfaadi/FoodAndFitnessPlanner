package com.planner.service;

import com.planner.entity.Admin;
import com.planner.entity.Query;
import com.planner.entity.Reply;
import com.planner.entity.User;

import java.util.List;

public interface AdminService {

    List<User> getAllUsers();

    boolean removeUserByUsername(String username);

    List<Query> getAllQueries();

    boolean replyToQuery(String queryId, String replyText, Admin admin);

    List<Reply> getRepliesForQuery(String queryId);
    
    void deleteAdmin(Admin admin);

}

