package ru.mirea.course.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content; // Текст вопроса

    @ElementCollection
    private List<String> options; // Варианты ответа

    @ElementCollection
    private List<Boolean> correctOptions; // Правильные ответы (true/false для каждого варианта)

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonBackReference
    private Test test; // Связь с тестом

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Boolean> getCorrectOptions() {
        return correctOptions;
    }

    public void setCorrectOptions(List<Boolean> correctOptions) {
        this.correctOptions = correctOptions;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
