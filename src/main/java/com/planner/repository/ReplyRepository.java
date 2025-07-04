package com.planner.repository;

import com.planner.entity.Reply;
import com.planner.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByQuery(Query query);
    List<Reply> findByQuery_QueryId(String queryId);

}

