package ru.mirea.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.course.model.Result;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentId(Long studentId);
    List<Result> findByTestId(Long testId);
}
