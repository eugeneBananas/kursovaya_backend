package ru.mirea.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.course.dto.QuestionRequest;
import ru.mirea.course.model.Question;
import ru.mirea.course.model.Test;
import ru.mirea.course.repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question addQuestion(QuestionRequest request) {
        Question question = new Question();
        question.setContent(request.getContent());
        question.setOptions(request.getOptions());
        question.setCorrectOptions(request.getCorrectOptions());

        Test test = new Test();
        test.setId(request.getTestId());
        question.setTest(test);

        return questionRepository.save(question);
    }

    // плучение вопросов по ID теста
    public List<Question> getQuestionsByTest(Long testId) {
        return questionRepository.findByTestId(testId);
    }
}
