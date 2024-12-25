package ru.mirea.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.course.dto.TestRequest;
import ru.mirea.course.dto.TestResponse;
import ru.mirea.course.model.Course;
import ru.mirea.course.model.Question;
import ru.mirea.course.model.Test;
import ru.mirea.course.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public TestResponse createTest(TestRequest testRequest) {
        // Создаем новый объект Test
        Test test = new Test();
        test.setTitle(testRequest.getTitle());
        test.setCourseId(testRequest.getCourseId());

        // Список вопросов
        List<Question> questions = new ArrayList<>();

        // Проходим по каждому вопросу в запросе
        for (TestRequest.QuestionRequest q : testRequest.getQuestions()) {
            Question question = new Question();
            question.setContent(q.getContent());  // Устанавливаем текст вопроса

            // Преобразуем список вариантов ответов
            List<String> options = new ArrayList<>();
            for (TestRequest.OptionRequest o : q.getOptions()) {
                options.add(o.getContent());
            }
            question.setOptions(options);  // Устанавливаем варианты ответа

            // Преобразуем правильные варианты
            List<Boolean> correctOptions = new ArrayList<>();
            for (TestRequest.OptionRequest o : q.getOptions()) {
                correctOptions.add(o.isCorrect());
            }
            question.setCorrectOptions(correctOptions);  // Устанавливаем правильные ответы

            question.setTest(test);  // Устанавливаем тест для каждого вопроса
            questions.add(question);  // Добавляем вопрос в список
        }

        test.setQuestions(questions);  // Устанавливаем все вопросы в тест

        // Сохраняем тест в базу данных
        test = testRepository.save(test);

        // Преобразуем Test в TestResponse перед возвращением
        return new TestResponse(test.getId(), test.getTitle(), test.getCourseId(), test.getQuestions());
    }

    public List<TestResponse> getTestsByCourse(Long courseId) {
        List<Test> tests = testRepository.findByCourseId(courseId);
        return tests.stream().map(test -> new TestResponse(test.getId(), test.getTitle(), courseId, test.getQuestions()))
                .collect(Collectors.toList());
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test getTestById(Long id) {
        return testRepository.findById(id).orElseThrow(() -> new RuntimeException("Test not found"));
    }

    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    public Test updateTest(Long id, Test updatedTest) {
        Test test = getTestById(id);
        test.setTitle(updatedTest.getTitle());
        test.setCourseId(updatedTest.getCourseId());
        test.setQuestions(updatedTest.getQuestions());
        return testRepository.save(test);
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}
