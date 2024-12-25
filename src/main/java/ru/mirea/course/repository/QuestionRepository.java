package ru.mirea.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.course.model.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTestId(Long testId);

}
