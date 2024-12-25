package ru.mirea.course.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String instructor;

    @ManyToMany
    @JoinTable(
            name = "course_group",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<StudyGroup> studyGroups;

    @ElementCollection
    @CollectionTable(name = "course_access_emails", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "email")
    private List<String> accessibleEmails;

    // Getters и Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public Set<StudyGroup> getGroups() { return studyGroups; }
    public void setGroups(Set<StudyGroup> studyGroups) { this.studyGroups = studyGroups; }

    public List<String> getAccessibleEmails() { return accessibleEmails; }
    public void setAccessibleEmails(List<String> accessibleEmails) { this.accessibleEmails = accessibleEmails; }
}
