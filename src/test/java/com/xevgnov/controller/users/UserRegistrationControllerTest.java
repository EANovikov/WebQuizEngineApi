package com.xevgnov.controller.users;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user1@test.com")
    public void getUsersReturnsAllUsersWithIdsAndEmailsTest() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "[{\"id\":1,\"email\":\"user1@test.com\"}," +
                                "{\"id\":2,\"email\":\"user2@test.com\"}]")));
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void getUsersDoesNotReturnPasswordsTest() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].password").doesNotExist());
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void registerUserWithUnusedValidEmailAndValidPasswordReturnsNewUserIdTest() throws Exception {
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"email\":\"user3@test.com\"," +
                        "\"password\":\"user3\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("User with ID [3] has been created.")));
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void registerUserWithUsedValidEmailAndValidPasswordReturnsBadRequesWithEmailUsedMessageTest() throws Exception {
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"email\":\"user2@test.com\"," +
                        "\"password\":\"user2\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("The email [user2@test.com] is already used! Please provide another email.")));
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void registerUserWithInvalidEmailReturnsBadRequestTest() throws Exception {
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"email\":\"user4test.com\"," +
                        "\"password\":\"user4\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user1@test.com")
    public void registerUserWithInvalidPasswordReturnsBadRequestTest() throws Exception {
        mockMvc.perform(post("/api/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(
                "{\"email\":\"user5@test.com\"," +
                        "\"password\":\"user\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}