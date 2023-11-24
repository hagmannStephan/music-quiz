package org.example;

import java.util.ArrayList;

public class Question {
    private String question;
    private ArrayList<QuestionValue> values;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<QuestionValue> getValues() {
        return values;
    }

    public void setValues(ArrayList<QuestionValue> values) {
        this.values = values;
    }
}
