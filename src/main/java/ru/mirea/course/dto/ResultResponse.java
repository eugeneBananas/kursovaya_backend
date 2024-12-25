package ru.mirea.course.dto;

import lombok.Data;

@Data
public class ResultResponse {
    private Long id;
    private Long studentId;
    private Long testId;
    private Double score;
    private Boolean passed;

    public ResultResponse(Long studentId, Long testId, Double score, Boolean passed) {
        this.studentId = studentId;
        this.testId = testId;
        this.score = score;
        this.passed = passed;
    }
}
