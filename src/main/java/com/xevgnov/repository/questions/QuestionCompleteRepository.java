package com.xevgnov.repository.questions;


import com.xevgnov.model.questions.QuestionComplete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCompleteRepository extends PagingAndSortingRepository<QuestionComplete, Integer> {

    Page<QuestionComplete> findAllQuestionCompleteByUser(String author, Pageable paging);

}
