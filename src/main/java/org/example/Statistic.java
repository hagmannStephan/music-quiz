package org.example;

public class Statistic {

    private String identifier; // Date + Username
    private int points;
    private int duration;
    private String user;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
                "identifier='" + identifier + '\'' +
                ", points=" + points +
                ", duration=" + duration +
                ", user='" + user + '\'' +
                '}';
    }
}
