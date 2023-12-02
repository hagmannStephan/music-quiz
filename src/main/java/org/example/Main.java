package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain){
            Game.play();
            boolean responded = false;
            while (!responded){
                System.out.println("Do you want to play again (yes/no)?");
                String response = scanner.next();
                switch (response){
                    case "yes" -> {responded = true;}
                    case "no" -> {responded = true; playAgain = false;}
                    default -> {
                        System.out.println("Please respond with a valid option.");
                    }
                }
            }
        }

    }
}