package ru.mirea.course.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.mirea.course.model.Course;
import ru.mirea.course.model.StudyGroup;
import ru.mirea.course.repository.CourseRepository;
import ru.mirea.course.repository.StudyGroupRepository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final RestTemplate restTemplate;

    // Конструктор с обоими репозиториями
    public CourseService(CourseRepository courseRepository, StudyGroupRepository studyGroupRepository, RestTemplate restTemplate) {
        this.courseRepository = courseRepository;
        this.studyGroupRepository = studyGroupRepository;
        this.restTemplate = restTemplate;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course updatedCourse) {
        Course course = getCourseById(id);
        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        course.setInstructor(updatedCourse.getInstructor());
        return courseRepository.save(course);
    }

    public UserDetails getUserDetails(Long userId) {
        String authServiceUrl = "http://localhost:8081//users/" + userId;
        return restTemplate.getForObject(authServiceUrl, UserDetails.class);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    public Course assignGroupsToCourse(Long courseId, Set<Long> groupIds) {
        Course course = getCourseById(courseId);
        Set<StudyGroup> studyGroups = studyGroupRepository.findAllById(groupIds).stream().collect(Collectors.toSet());
        course.setGroups(studyGroups);
        return courseRepository.save(course);
    }

    public Course addEmailAccess(Long courseId, String email) {
        Course course = getCourseById(courseId);
        if (!course.getAccessibleEmails().contains(email)) {
            course.getAccessibleEmails().add(email);
        }
        return courseRepository.save(course);
    }

    public Course removeEmailAccess(Long courseId, String email) {
        Course course = getCourseById(courseId);
        course.getAccessibleEmails().remove(email);
        return courseRepository.save(course);
    }

    public List<String> getAccessibleEmails(Long courseId) {
        return getCourseById(courseId).getAccessibleEmails();
    }
}
