package com.xevgnov.service.questions;


import com.xevgnov.model.questions.QuestionComplete;
import org.springframework.data.domain.Page;

public interface QuestionCompleteService {

    Page<QuestionComplete> findAllQuestionCompleteByUser(String user, Integer pageNo, Integer pageSize, String sortBy);

    void saveQuestionCompleteStatus(QuestionComplete questionComplete);

}
