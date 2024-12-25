package ru.mirea.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.course.dto.QuestionRequest;
import ru.mirea.course.model.Question;
import ru.mirea.course.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionRequest request) {
        Question question = questionService.addQuestion(request);
        return ResponseEntity.ok(question);
    }

    @GetMapping("/{testId}")
    public ResponseEntity<List<Question>> getQuestionsByTest(@PathVariable Long testId) {
        List<Question> questions = questionService.getQuestionsByTest(testId);
        return ResponseEntity.ok(questions);
    }
}
