import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain;

        do {
            int numberToGuess = random.nextInt(100) + 1;
            int maxTries = 3;
            boolean correctGuess = false;

            System.out.println("Welcome to the Number Guessing Game! Guess the number from 1 to 100.");
            System.out.println("You have " + maxTries + " tries to guess the number.");

            for (int i = 0; i < maxTries; i++) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();

                if (guess < 1 || guess > 100) {
                    System.out.println("Please enter a number between 1 and 100.");
                } 
                else if (guess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } 
                else if (guess > numberToGuess) {
                    System.out.println("Too high! Try again.");
                } 
                else {
                    System.out.println("Congratulations! You've guessed the number.");
                    correctGuess = true;
                    break;
                }
            }

            if (!correctGuess) {
                System.out.println("Sorry, you've used all your attempts. The number was " + numberToGuess + ".");
            }

            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        } 
        while (playAgain);

        scanner.close();
        System.out.println("Thanks for playing! Goodbye!");
    }
}
