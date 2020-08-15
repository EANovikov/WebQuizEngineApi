package com.xevgnov.service.questions;

import com.xevgnov.model.questions.Question;
import com.xevgnov.repository.questions.QuestionCompleteRepository;
import com.xevgnov.repository.questions.QuestionRepository;
import com.xevgnov.repository.users.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceImplTest {

    @Autowired
    @Qualifier("questionServiceImpl")
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @MockBean
    private QuestionCompleteRepository questionCompleteRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findQuestionByIdSuccessfullyReturnsExistingQuestion() {
        Question question = questionService.findQuestionById(1);
        assertThat(question).isNotNull();
        assertThat(question.getId()).isEqualTo(1);
        assertThat(question.getTitle()).isEqualTo("question1");
        assertThat(question.getText()).isEqualTo("text1");
        assertThat(question.getAuthor()).isEqualTo("user1@test.com");
        assertThat(question.getOptions()).containsExactlyInAnyOrder("a", "b", "c", "d");
    }

    @Test
    public void findQuestionByIdReturnsNullForNotExistingQuestion() {
        Question question = questionService.findQuestionById(123);
        assertThat(question).isNull();
    }

    @Test
    public void findAllQuestions() {
        Page<Question> pages = questionService.findAllQuestions(0, 100, "id");
        List<Question> questions = pages.getContent();
        assertThat(questions.size()).isEqualTo(3);
        assertThat(questions.get(0).getTitle()).isEqualTo("question1");
        assertThat(questions.get(1).getTitle()).isEqualTo("question2");
        assertThat(questions.get(2).getTitle()).isEqualTo("question3");
    }


    @Test
    public void createQuestion() {
        Question question = new Question("question4", "text4", Arrays.asList("x", "y", "z"), Arrays.asList(0, 1, 2));
        questionService.createQuestion(question);
        Question dbQuestion = questionService.findQuestionById(4);
        assertThat(dbQuestion.getTitle()).isEqualTo(question.getTitle());
        assertThat(dbQuestion.getText()).isEqualTo(question.getText());
        assertThat(dbQuestion.getOptions()).containsExactlyInAnyOrder("x", "y", "z");
    }

    @Test
    public void deleteQuestion() {
        Question question = new Question("question4", "text4", Arrays.asList("x", "y", "z"), Arrays.asList(0, 1, 2));
        questionService.createQuestion(question);
        questionService.deleteQuestion(4);
        Question dbQuestion = questionService.findQuestionById(4);
        assertThat(dbQuestion).isNull();
    }
}