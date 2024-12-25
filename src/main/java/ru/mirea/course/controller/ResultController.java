package ru.mirea.course.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.course.dto.ResultResponse;
import ru.mirea.course.model.Result;
import ru.mirea.course.service.ResultService;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @PostMapping
    public ResponseEntity<ResultResponse> saveResult(@RequestBody Result result) {
        ResultResponse response = resultService.saveResult(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<ResultResponse>> getResultsByStudent(@PathVariable Long studentId) {
        List<ResultResponse> results = resultService.getResultsByStudent(studentId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<List<ResultResponse>> getResultsByTest(@PathVariable Long testId) {
        List<ResultResponse> results = resultService.getResultsByTest(testId);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResultResponse>> getAllResults() {
        List<ResultResponse> results = resultService.getAllResults();
        return ResponseEntity.ok(results);
    }

    @PostMapping("/{testId}")
    public ResponseEntity<ResultResponse> submitTest(@PathVariable Long testId, @RequestBody List<Integer> answerIndices) {
        ResultResponse result = resultService.submitTest(testId, answerIndices);
        return ResponseEntity.ok(result);
    }
}
