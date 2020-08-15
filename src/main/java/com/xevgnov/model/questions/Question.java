package com.xevgnov.model.questions;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "The title cannot be missing or empty")
    private String title;

    @Column(name = "text", nullable = false)
    @NotBlank(message = "The text cannot be missing or empty")
    private String text;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "options")
    @NotNull(message = "The options field cannot null")
    @Size(min = 2, message = "The question has to contain 2 or more options")
    private List<String> options;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "answer")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Integer> answer;

    @JsonIgnore
    @Column(name = "author")
    private String author;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
    private List<QuestionComplete> completed;

    public Question(String title, String text, List<String> options, List<Integer> answer) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<QuestionComplete> getCompleted() {
        return completed;
    }

    public void setCompleted(List<QuestionComplete> completed) {
        this.completed = completed;
    }

}
