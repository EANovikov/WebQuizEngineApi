package com.xevgnov.model.questions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "question_complete")
public class QuestionComplete {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complete_id", nullable = false, unique = true)
    private int completeId;

    @JsonIgnore
    @Column(name = "user")
    private String user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question")
    private Question question;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "question_id", nullable = false)
    private int id;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    public QuestionComplete() {
    }

    public QuestionComplete(Question question, String user) {
        this.question = question;
        this.user = user;
        this.completedAt = LocalDateTime.now();
        this.id = question.getId();
    }

    public int getCompleteId() {
        return completeId;
    }

    public void setCompleteId(int completeId) {
        this.completeId = completeId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
