package ru.mirea.course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.course.model.StudyGroup;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
}
