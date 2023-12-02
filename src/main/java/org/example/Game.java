package org.example;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static void play() {
        Statistic stat = new Statistic();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println();
        System.out.println("Welcome to music-quiz!");
        System.out.println("------------------------------------");
        System.out.print("Please enter your name: ");

        String name = scanner.next();
        stat.setUser(name);

        System.out.println();
        System.out.println("Hello " + name);

        boolean responded = false;

        while (!responded){
            responded = true;
            System.out.println("Which of the following categories do you want to be questioned about: maxStreams, release, length or titles?");
            String category = scanner.next();
            System.out.println();

            switch (category) {
                case "maxStreams" -> System.out.println("Great choice, your task is to guess: How many copies of the album got sold (in mil)?");
                case "release" -> System.out.println("Great choice, your task is to guess: In which year was the album released?");
                case "length" -> System.out.println("Great choice, your task is to guess: How long does it take to play the album (in min)?");
                case "titles" -> System.out.println("Great choice, your task is to guess: How many tracks are on the album?");
                default -> {
                    System.out.println("Please enter a valid category next time");
                    responded = false;
                }
            }
            stat.setCategory(category);
        }

        long startTime = System.currentTimeMillis();
        ArrayList<Question> finishedQuestions = new ArrayList<>();

        while (finishedQuestions.size() <= 5) {
            Question current = QuizDBConnector.getRandomEntry(stat.getCategory());
            Boolean alreadyAsked = false;
            for (Question q : finishedQuestions){
                if (q.getQuestion().equals(current.getQuestion())){
                    alreadyAsked = true;
                    break;
                }
            }
            if (!alreadyAsked) {
                System.out.println();
                System.out.println(current.getQuestion());
                ArrayList<QuestionValue> values = new ArrayList<>(current.getValues());
                int originalSize = values.size();

                while (values.size() > 0) {
                    QuestionValue question = values.get(random.nextInt(values.size()));
                    System.out.println(originalSize - values.size() + 1 + ") " + question.getDescription());
                    values.remove(question);
                }

                System.out.println("What do you think it the right answer (Enter the value you believe to be true): ");
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
