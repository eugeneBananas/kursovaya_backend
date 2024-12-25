package ru.mirea.course.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Test {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "course_id")
    private Long courseId; // ID курса из Course-сервиса

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "test_id") // Это связывает вопросы с тестом
    @JsonManagedReference  // Указывает, что это сторона, которая управляет сериализацией
    private List<Question> questions; // Вопросы, которые будут частью теста

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    // Геттеры и сеттеры
}
