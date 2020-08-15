package com.xevgnov.model.questions;

import org.junit.Test;

import static com.xevgnov.model.questions.ResultFactory.getResult;
import static org.assertj.core.api.Assertions.assertThat;

public class ResultFactoryTest {

    @Test
    public void getResultAcceptsTrueAndReturnsCongratulationsTest() {
        Result result = getResult(true);
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getFeedback()).isEqualTo("Congratulations, you're right!");
    }

    @Test
    public void getResultAcceptsFalseAndReturnsWrongAnswerTest() {
        Result result = getResult(false);
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getFeedback()).isEqualTo("Wrong answer! Please, try again.");
    }

}