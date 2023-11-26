package org.example;

public class EntryClass {
    private String name;
    private int maxStreams;
    private int release;
    private int length;
    private int titles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxStreams() {
        return maxStreams;
    }

    public void setMaxStreams(int maxStreams) {
        this.maxStreams = maxStreams;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTitles() {
        return titles;
    }

    public void setTitles(int titles) {
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "EntryClass{" +
                "name='" + name + '\'' +
                ", maxStreams=" + maxStreams +
                ", release=" + release +
                ", length=" + length +
                ", titles=" + titles +
                '}';
    }
}
