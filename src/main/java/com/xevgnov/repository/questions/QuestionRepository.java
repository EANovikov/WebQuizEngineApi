package com.xevgnov.repository.questions;

import com.xevgnov.model.questions.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Integer> {

    Question findQuestionById(Integer id);

    List<Question> findAll();

}
