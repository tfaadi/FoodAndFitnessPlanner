package com.planner.service;

import com.planner.entity.Admin;
import com.planner.entity.Query;
import com.planner.entity.Reply;
import com.planner.entity.User;
import com.planner.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final QueryRepository queryRepository;
    private final ReplyRepository replyRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository,
                            QueryRepository queryRepository,
                            ReplyRepository replyRepository, 
                            AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.queryRepository = queryRepository;
        this.replyRepository = replyRepository;
        this.adminRepository= adminRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean removeUserByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            userRepository.delete(userOpt.get());
            return true;
        }
        return false;
    }

    @Override
    public List<Query> getAllQueries() {
        return queryRepository.findAllByOrderByTimestampDesc();
    }

    @Override
    public boolean replyToQuery(String queryId, String replyText, Admin admin) {
        Optional<Query> queryOpt = queryRepository.findByQueryId(queryId);
        if (queryOpt.isPresent()) {
            Query query = queryOpt.get();
            Reply reply = new Reply(replyText, query, admin);
            replyRepository.save(reply);
            return true;
        }
        return false;
    }

    @Override
    public List<Reply> getRepliesForQuery(String queryId) {
        Optional<Query> queryOpt = queryRepository.findByQueryId(queryId);
        return queryOpt.map(replyRepository::findByQuery).orElse(null);
    }
    
    @Override
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

}

