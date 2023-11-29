package org.example;

public class Statistic {

    private int points = 0;
    private int duration;
    private String user;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "points=" + points +
                ", duration=" + duration +
                ", user='" + user + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
