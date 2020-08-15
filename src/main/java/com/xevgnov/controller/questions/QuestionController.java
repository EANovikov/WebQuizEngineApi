package com.xevgnov.controller.questions;

import com.xevgnov.model.questions.*;
import com.xevgnov.service.questions.QuestionCompleteService;
import com.xevgnov.service.questions.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionCompleteService questionCompleteService;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionCompleteService questionCompleteService) {
        this.questionService = questionService;
        this.questionCompleteService = questionCompleteService;
    }

    @PostMapping("/quizzes")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Question question, Principal principal) {
        question.setAuthor(principal.getName());
        questionService.createQuestion(question);
        return ResponseEntity.ok(question);
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<?> solveQuestion(@PathVariable int id,
                                           @RequestBody Answer answers, Principal principal) {
        if (answers == null) {
            return ResponseEntity.badRequest().body("Invalid request format: please provide an answer");
        }

        Question questionDao = questionService.findQuestionById(id);

        if (questionDao != null) {
            List<Integer> usersAnswer = Arrays.stream(answers.getAnswer()).boxed().collect(Collectors.toList());
            boolean isCorrect = usersAnswer.equals(questionDao.getAnswer());
            Result result = ResultFactory.getResult(isCorrect);

            if (result.isSuccess()) {
                questionCompleteService.saveQuestionCompleteStatus(new QuestionComplete(questionDao, principal.getName()));
            }

            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable int id) {
        Question questionDao = questionService.findQuestionById(id);
        if (questionDao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionDao);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Page<Question>> getQuestions(@RequestParam(defaultValue = "0", name = "page") Integer pageNo,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(questionService.findAllQuestions(pageNo, pageSize, sortBy));
    }

    @DeleteMapping("/quizzes/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int id, Principal principal) {
        Question questionDao = questionService.findQuestionById(id);
        if (questionDao == null) {
            return ResponseEntity.notFound().build();
        }

        if (questionDao.getAuthor() != null && questionDao.getAuthor().equals(principal.getName())) {
            questionService.deleteQuestion(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Impossible to delete the question owned by different author!");
        }
    }

    @GetMapping("/quizzes/completed")
    public ResponseEntity<Page<QuestionComplete>> getCompleteQuestions(Principal principal,
                                                                       @RequestParam(defaultValue = "0", name = "page") Integer pageNo,
                                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                                       @RequestParam(defaultValue = "completedAt") String sortBy) {
        return ResponseEntity.ok(questionCompleteService.findAllQuestionCompleteByUser(principal.getName(), pageNo, pageSize, sortBy));
    }

}
