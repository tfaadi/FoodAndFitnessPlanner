package com.planner.service;

import com.planner.entity.Query;
import com.planner.entity.User;

import java.util.List;
import java.util.Optional;

public interface QueryService {

    boolean postQuery(String content, User user);

    List<Query> getAllQueries();

    List<Query> getQueriesByUser(User user);
    
    Query getQueryByQueryId(String queryId);
    
    void deleteQueryById(String queryId);



}

