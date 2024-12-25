package ru.mirea.course.dto;

import java.util.List;

public class QuestionRequest {

    private String content; // Текст вопроса
    private List<String> options; // Варианты ответа
    private List<Boolean> correctOptions; // Правильные варианты ответа (true/false для каждого варианта)
    private Long testId; // ID теста, к которому принадлежит вопрос

    // Getters и Setters
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

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }
}
