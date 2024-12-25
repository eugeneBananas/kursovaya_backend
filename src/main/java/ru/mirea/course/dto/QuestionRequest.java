package ru.mirea.course.dto;

import java.util.List;

public class QuestionRequest {

    private String content;
    private List<String> options;
    private List<Boolean> correctOptions;
    private Long testId;

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
