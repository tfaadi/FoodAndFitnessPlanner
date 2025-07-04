package com.planner.repository;

import com.planner.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {
    Optional<Query> findByQueryId(String queryId);
    List<Query> findAllByOrderByTimestampDesc();
}

