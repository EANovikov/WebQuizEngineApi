package com.xevgnov.service.questions;

import com.xevgnov.model.questions.Question;
import com.xevgnov.model.questions.QuestionComplete;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionCompleteServiceImplTest {

    @Mock
    private Question question;

    private QuestionComplete questionComplete;

    @Autowired
    @Qualifier("questionCompleteServiceImpl")
    private QuestionCompleteService questionCompleteService;

    @Before
    public void init() {
        when(question.getId()).thenReturn(1);
        questionComplete = new QuestionComplete(question, "user1@test.com");
    }

    @Test
    public void findAllQuestionCompleteByUserReturnsSortedResultInDescOrderTest() {
        Page<QuestionComplete> questionCompletePage = questionCompleteService.findAllQuestionCompleteByUser("user1@test.com", 0, 100, "completeId");
        assertThat(questionCompletePage.getContent()).isNotEmpty();
        List<QuestionComplete> questionCompletes = questionCompletePage.getContent();
        assertThat(questionCompletes.size()).isEqualTo(2);
        assertThat(questionCompletes.get(0).getCompleteId()).isEqualTo(2);
        assertThat(questionCompletes.get(0).getId()).isEqualTo(2);
        assertThat(questionCompletes.get(0).getCompletedAt().toString()).isEqualTo("2020-07-11T08:25:51");
    }

    @Test
    public void findAllQuestionCompleteByUserReturnsSortedResultForProperPageAndPageSizeTest() {
        Page<QuestionComplete> questionCompletePage = questionCompleteService.findAllQuestionCompleteByUser("user1@test.com", 1, 1, "completedAt");
        assertThat(questionCompletePage.getContent()).isNotEmpty();
        List<QuestionComplete> questionCompletes = questionCompletePage.getContent();
        assertThat(questionCompletes.size()).isEqualTo(1);
        assertThat(questionCompletes.get(0).getCompleteId()).isEqualTo(1);
        assertThat(questionCompletes.get(0).getId()).isEqualTo(1);
        assertThat(questionCompletes.get(0).getCompletedAt().toString()).isEqualTo("2020-07-11T08:25:50");
    }


    @Test
    public void saveQuestionCompleteStatusSavesCompletedQuestionCorrectlyTest() {
        questionComplete.setQuestion(new Question("a", "b", Arrays.asList("1", "2"), Arrays.asList(1, 2)));
        questionComplete.setId(3);
        questionCompleteService.saveQuestionCompleteStatus(questionComplete);
        Page<QuestionComplete> questionCompletePage = questionCompleteService.findAllQuestionCompleteByUser("user1@test.com", 0, 100, "completedAt");
        assertThat(questionCompletePage.getContent()).isNotEmpty();
        List<QuestionComplete> questionCompletes = questionCompletePage.getContent();
        assertThat(questionCompletes.size()).isEqualTo(3);
        assertThat(questionCompletes.get(0).getCompleteId()).isEqualTo(3);
        assertThat(questionCompletes.get(0).getId()).isEqualTo(3);
    }
}