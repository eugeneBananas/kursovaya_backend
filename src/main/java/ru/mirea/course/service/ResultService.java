package ru.mirea.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.course.dto.ResultResponse;
import ru.mirea.course.model.Question;
import ru.mirea.course.model.Result;
import ru.mirea.course.repository.QuestionRepository;
import ru.mirea.course.repository.ResultRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<ResultResponse> getResultsByStudent(Long studentId) {
        List<Result> results = resultRepository.findByStudentId(studentId);

        return results.stream()
                .map(result -> new ResultResponse(
                        result.getStudentId(),
                        result.getTest().getId(),  // Получаем ID теста через объект Test
                        result.getScore(),
                        result.isPassed()))
                .collect(Collectors.toList());
    }


    public List<ResultResponse> getResultsByTest(Long testId) {
        List<Result> results = resultRepository.findByTestId(testId);
        return results.stream()
                .map(result -> new ResultResponse(
                        result.getStudentId(),
                        result.getTest().getId(),
                        result.getScore(),
                        result.isPassed()))
                .collect(Collectors.toList());
    }

    public ResultResponse submitTest(Long testId, List<Integer> answerIndices) {
        List<Question> questions = questionRepository.findByTestId(testId);

        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (Question question : questions) {
            List<Boolean> correctOptions = question.getCorrectOptions();

            for (Integer index : answerIndices) {
                if (index >= 0 && index < correctOptions.size() && correctOptions.get(index)) {
                    correctAnswers++;
                    break;
                }
            }
        }

        double score = (double) correctAnswers / totalQuestions * 100;
        boolean passed = score >= 50;

        Result result = new Result();
        result.setStudentId(getCurrentStudentId());
        result.setTest(questions.get(0).getTest());
        result.setScore(score);
        result.setPassed(passed);

        result = resultRepository.save(result);

        return new ResultResponse(result.getStudentId(), result.getTest().getId(), score, passed);
    }

    private Long getCurrentStudentId() {
        return 1L;
    }

    public List<ResultResponse> getAllResults() {
        List<Result> results = resultRepository.findAll();

        return results.stream()
                .map(result -> new ResultResponse(
                        result.getStudentId(),
                        result.getTest() != null ? result.getTest().getId() : null,
                        result.getScore(),
                        result.isPassed()))
                .collect(Collectors.toList());
    }

    public ResultResponse saveResult(Result result) {
        Result savedResult = resultRepository.save(result);

        return new ResultResponse(
                savedResult.getStudentId(),
                savedResult.getTest().getId(),
                savedResult.getScore(),
                savedResult.isPassed());
    }
}