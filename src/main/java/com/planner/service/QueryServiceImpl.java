package com.planner.service;

import com.planner.entity.Query;
import com.planner.entity.User;
import com.planner.repository.QueryRepository;

import com.planner.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryServiceImpl implements QueryService {

    private final QueryRepository queryRepository;

    public QueryServiceImpl(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Override
    public boolean postQuery(String content, User user) {
        String queryId = IdGeneratorUtil.generateQueryId(); 
        Query query = new Query(queryId, content, user);
        queryRepository.save(query);
        return true;
    }

    @Override
    public List<Query> getAllQueries() {
        return queryRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    public List<Query> getQueriesByUser(User user) {
        return user.getQueries(); // or queryRepository.findByUser(user) if you want to add that
    }
    
    @Override
    public Query getQueryByQueryId(String queryId) {
        return queryRepository.findByQueryId(queryId).orElse(null);
    }

    @Override
    public void deleteQueryById(String queryId) {
        queryRepository.findByQueryId(queryId).ifPresent(queryRepository::delete);
    }


}

