package org.example;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static void play() {
        Statistic stat = new Statistic();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to music-quiz!");
        System.out.println("------------------------------------");
        System.out.print("What is your name: ");

        String name = scanner.next();
        stat.setUser(name);

        System.out.println("Hello " + name);
        System.out.println("Which of the following categories do you want to be questioned about: maxStreams, release, length or titles");

        String category = scanner.next();

        switch (category) {
            case "maxStreams" -> System.out.println("You will have to guess which of the numbers (in millions) seems most likely to by the number of streams on the most streamed song of the album");
            case "release" -> System.out.println("You will have to guess which year the album was released");
            case "length" -> System.out.println("You will have to guess how long the album is (in minutes)");
            case "titles" -> System.out.println("You will have to guess how many titles there are on the album");
            default -> {
                System.out.println("Please enter a valid category next time");
                System.exit(0);
            }
        }

        stat.setCategory(category);

        long startTime = System.currentTimeMillis();
        ArrayList<Question> finishedQuestions = new ArrayList<>();

        while (finishedQuestions.size() <= 5) {
            Question current = QuizDBConnector.getRandomEntry(category);
            Boolean alreadyAsked = false;
            for (Question q : finishedQuestions){
                if (q.getQuestion().equals(current.getQuestion())){
                    alreadyAsked = true;
                    break;
                }
            }
            if (!alreadyAsked) {
                System.out.println();
                System.out.println("Album name: " + current.getQuestion());
                ArrayList<QuestionValue> values = new ArrayList<>(current.getValues());
                int originalSize = values.size();

                while (values.size() > 0) {
                    QuestionValue question = values.get(random.nextInt(values.size()));
                    System.out.println(originalSize - values.size() + 1 + ") " + question.getDescription());
                    values.remove(question);
                }

                System.out.println("What do you think it the right answer (Type the value you believe is true): ");
                int response = scanner.nextInt();
                boolean isCorrect = false;
                for (QuestionValue v : current.getValues()) {
                    if (v.getDescription() == response){
                        if(v.getTrue()) {
                            System.out.println("Great job, this is correct!");
                            isCorrect = true;
                            stat.setPoints(stat.getPoints() + 1);
                        }
                    }
                }
                if (!isCorrect) {
                    System.out.println("This was wrong, try again next time");
                }
                finishedQuestions.add(current);
            }
        }
        long endTime = System.currentTimeMillis();
        stat.setDuration((int) ((endTime - startTime) / 1000));
        System.out.println("You managed to get " +  String.valueOf(stat.getPoints()) + " Points in " + String.valueOf(stat.getDuration() + " Seconds!"));
        QuizDBConnector.addStatistic(stat);

        System.out.println();
        System.out.println("------------------------------------");
        System.out.println("The current high-scores are:");
        ArrayList<Statistic> statistics = QuizDBConnector.getTopThree();
        for (Statistic s : statistics) {
            int place = statistics.indexOf(s) + 1;
            System.out.println(String.valueOf(place) + ") " + s.getUser() + " in " + String.valueOf(s.getDuration()) + " seconds with " + String.valueOf(s.getPoints()) + " points");
        }
    }
}
