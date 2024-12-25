package ru.mirea.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.mirea.course.model.Course;
import ru.mirea.course.service.CourseService;
import java.util.Set;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Autowired
    private RestTemplate restTemplate;

    private final String AUTH_SERVICE_URL = "http://localhost:8081/api/auth/validateUser";


    public boolean isUserRegistered(String email) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    AUTH_SERVICE_URL + "?email=" + email, HttpMethod.GET, null, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            return false;
        }
    }

    //привязка курса к группам
    @PostMapping("/{courseId}/assign-groups")
    public ResponseEntity<Course> assignGroups(@PathVariable Long courseId, @RequestBody Set<Long> groupIds) {
        Course course = courseService.assignGroupsToCourse(courseId, groupIds);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        return ResponseEntity.ok(courseService.updateCourse(id, updatedCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/add-email")
    public ResponseEntity<Course> addEmailAccess(@PathVariable Long courseId, @RequestBody String email) {
        Course updatedCourse = courseService.addEmailAccess(courseId, email);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{courseId}/remove-email")
    public ResponseEntity<Course> removeEmailAccess(@PathVariable Long courseId, @RequestBody String email) {
        Course updatedCourse = courseService.removeEmailAccess(courseId, email);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/{courseId}/emails")
    public ResponseEntity<List<String>> getAccessibleEmails(@PathVariable Long courseId) {
        List<String> emails = courseService.getAccessibleEmails(courseId);
        return ResponseEntity.ok(emails);
    }
}
