package com.accesa.project.questions;

public class Question {
    private int id;
    private String question;
    private Boolean answer;
    private Boolean approval;
    private int rewards;

    public Question(int id, String question, Boolean answer, Boolean approval, int rewards) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.approval = approval;
        this.rewards = rewards;
    }


    public String getQuestion() {
        return question;
    }

    public Boolean isAnswer() {
        return answer;
    }

    public Boolean isApproved() {
        return approval;
    }

    public int getRewards() {
        return rewards;
    }

    public int getId() {
        return id;
    }
}
