package com.xevgnov.model.questions;

public class ResultFactory {

    private static final String CORRECT_ANSWER = "Congratulations, you're right!";
    private static final String WRONG_ANSWER = "Wrong answer! Please, try again.";

    public static Result getResult(boolean correct) {
        if (correct) {
            return new Result(true, CORRECT_ANSWER);
        } else {
            return new Result(false, WRONG_ANSWER);
        }
    }

}
