package ru.mirea.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.course.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
