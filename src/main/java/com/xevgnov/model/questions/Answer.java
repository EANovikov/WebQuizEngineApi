package com.xevgnov.model.questions;

public class Answer {

    private int[] answer;

    public Answer(int[] answer) {
        this.answer = answer;
    }

    public Answer() {
    }

    public int[] getAnswer() {
        return answer;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
}
