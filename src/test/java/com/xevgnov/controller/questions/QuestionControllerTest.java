package com.xevgnov.controller.questions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user1@test.com")
    public void getQuestionsReturnsContentWithAllQuestionsTest() throws Exception {
        mockMvc.perform(get("/api/quizzes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(content().string(containsString(
                        "\"content\":[" +
                                "{\"id\":1,\"title\":\"question1\",\"text\":\"text1\",\"options\":[\"a\",\"b\",\"c\",\"d\"]}," +
                                "{\"id\":2,\"title\":\"question2\",\"text\":\"text2\",\"options\":[\"a\",\"b\"]}]")))
                .andExpect(jsonPath("$[*].pageSize").value(10));
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void getQuestionReturnsExistingQuestionByIdTest() throws Exception {
        mockMvc.perform(get("/api/quizzes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"id\":1," +
                                "\"title\":\"question1\"," +
                                "\"text\":\"text1\"," +
                                "\"options\":[\"a\",\"b\",\"c\",\"d\"]}")));

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void getQuestionByNotExistingIdReturnsNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/quizzes/10"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void getQuestionDoesNotShowTheAnswerTest() throws Exception {
        mockMvc.perform(get("/api/quizzes/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].answer").doesNotExist());

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void createQuestionReturnsIdTitleTextAndOptionsTest() throws Exception {
        mockMvc.perform(post("/api/quizzes").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"title\":\"question4\"," +
                        "\"text\":\"text4\"," +
                        "\"options\":[\"a\",\"b\",\"c\",\"d\"]," +
                        "\"answer\":[0,3]" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"id\":4," +
                                "\"title\":\"question4\"," +
                                "\"text\":\"text4\"," +
                                "\"options\":[\"a\",\"b\",\"c\",\"d\"]}")));

        mockMvc.perform(get("/api/quizzes/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"id\":4," +
                                "\"title\":\"question4\"," +
                                "\"text\":\"text4\"," +
                                "\"options\":[\"a\",\"b\",\"c\",\"d\"]}")));

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void createQuestionDoesNotReturnsTheAnswerTest() throws Exception {
        mockMvc.perform(post("/api/quizzes").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"title\":\"question4\"," +
                        "\"text\":\"text4\"," +
                        "\"options\":[\"a\",\"b\",\"c\",\"d\"]," +
                        "\"answer\":[3]" +
                        "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].answer").doesNotExist());

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void solveQuestionWithWrongAnswerReturnsSuccessFalseAndFeedbackWrongAnswerTest() throws Exception {
        mockMvc.perform(post("/api/quizzes/1/solve").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"answer\": [0]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"success\":false," +
                                "\"feedback\":\"Wrong answer! Please, try again.\"}")));

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void solveQuestionWithCorrectAnswerReturnsSuccessTrueAndFeedbackCongratulationsTest() throws Exception {
        mockMvc.perform(post("/api/quizzes/1/solve").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"answer\": [0,1]}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "{\"success\":true," +
                                "\"feedback\":\"Congratulations, you're right!\"}")));

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void solveQuestionWithNotExistingIdReturnsNotFoundTest() throws Exception {
        mockMvc.perform(post("/api/quizzes/55/solve").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"answer\": [0,1]}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void deleteQuestionByExistingIdReturnsNoContentTest() throws Exception {
        mockMvc.perform(delete("/api/quizzes/3"))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/quizzes/3"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void deleteQuestionOwnedByOtherAuthorReturnsForbiddenTest() throws Exception {
        mockMvc.perform(delete("/api/quizzes/2"))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void deleteQuestionByNotExistingIdReturnsNotFoundTest() throws Exception {
        mockMvc.perform(delete("/api/quizzes/99"))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void getCompleteQuestionsReturnsQuestionIdsAndCompletedAtForUser1InDescOrderTest() throws Exception {
        mockMvc.perform(get("/api/quizzes/completed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"content\":[" +
                                "{\"id\":2,\"completedAt\":\"2020-07-11T08:25:51\"}," +
                                "{\"id\":1,\"completedAt\":\"2020-07-11T08:25:50\"}]")))
                .andExpect(jsonPath("$[*].pageSize").value(10));
        ;

    }

    @Test
    @WithMockUser(username = "user2@test.com")
    public void getCompleteQuestionsDoesNotReturnCompletedQuestionsForUser2Test() throws Exception {
        mockMvc.perform(get("/api/quizzes/completed"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"content\":[]")))
                .andExpect(jsonPath("$[*].pageSize").value(10));

    }

}