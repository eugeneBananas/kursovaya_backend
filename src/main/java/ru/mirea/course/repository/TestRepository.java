package ru.mirea.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.course.model.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByCourseId(Long courseId);
}
