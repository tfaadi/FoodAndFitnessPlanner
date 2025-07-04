package com.planner.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "replies")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String replyText;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "query_id")
    private Query query;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    public Reply() {
        this.timestamp = LocalDateTime.now();
    }

    public Reply(String replyText, Query query, Admin admin) {
        this.replyText = replyText;
        this.query = query;
        this.admin = admin;
        this.timestamp = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}

