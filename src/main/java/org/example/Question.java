package org.example;

public class Question {
    private String name;
    private int releaseYear;
    private int soldUnits;
    private int length;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getSoldUnits() {
        return soldUnits;
    }

    public void setSoldUnits(int soldUnits) {
        this.soldUnits = soldUnits;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Question{" +
                "name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", soldUnits=" + soldUnits +
                ", length=" + length +
                '}';
    }
}
