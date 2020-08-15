package com.xevgnov.service.questions;

import com.xevgnov.model.questions.QuestionComplete;
import com.xevgnov.repository.questions.QuestionCompleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class QuestionCompleteServiceImpl implements QuestionCompleteService {

    private final QuestionCompleteRepository questionCompleteRepository;

    @Autowired
    public QuestionCompleteServiceImpl(QuestionCompleteRepository questionCompleteRepository) {
        this.questionCompleteRepository = questionCompleteRepository;
    }

    @Override
    public Page<QuestionComplete> findAllQuestionCompleteByUser(String user, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<QuestionComplete> pagedResult = questionCompleteRepository.findAllQuestionCompleteByUser(user, paging);
        return pagedResult;
    }

    @Override
    public void saveQuestionCompleteStatus(QuestionComplete questionComplete) {
        questionCompleteRepository.save(questionComplete);
    }

}
