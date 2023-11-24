package org.example;

public class QuestionValue {
    private Integer description;
    private Boolean isTrue;

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public Boolean getTrue() {
        return isTrue;
    }

    public void setTrue(Boolean aTrue) {
        isTrue = aTrue;
    }

    public QuestionValue(Integer description, Boolean isTrue) {
        this.description = description;
        this.isTrue = isTrue;
    }

    @Override
    public String toString() {
        return "QuestionValue{" +
                "description=" + description +
                ", isTrue=" + isTrue +
                '}';
    }
}
