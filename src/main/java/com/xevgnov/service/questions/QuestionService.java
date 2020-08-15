package com.xevgnov.service.questions;


import com.xevgnov.model.questions.Question;
import org.springframework.data.domain.Page;

public interface QuestionService {

    Question findQuestionById(Integer id);

    Page<Question> findAllQuestions(Integer pageNo, Integer pageSize, String sortBy);

    void createQuestion(Question question);

    void deleteQuestion(int id);
}
