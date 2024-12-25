package ru.mirea.course.dto;

import java.util.List;

public class TestRequest {

    private String title;
    private Long courseId;
    private List<QuestionRequest> questions;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<QuestionRequest> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionRequest> questions) {
        this.questions = questions;
    }

    public static class QuestionRequest {
        private String content;
        private List<OptionRequest> options;
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<OptionRequest> getOptions() {
            return options;
        }

        public void setOptions(List<OptionRequest> options) {
            this.options = options;
        }
    }

    public static class OptionRequest {
        private String content;
        private boolean correct;

        // Геттеры и сеттеры

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }
}
