package org.example;

public class EntryClass {
    private String question;
    private Integer description;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EntryClass{" +
                "question='" + question + '\'' +
                ", description=" + description +
                '}';
    }
}
