import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class NumberGuess {
    Scanner input = new Scanner(System.in);

    int userinput, choice, lowattempt;
    LinkedList<Integer> LowestAttempt = new LinkedList<Integer>();

    int NumberGenarate() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    void checkResult() {

        do {
            int GenaratedNumber = NumberGenarate();
            // System.out.println("Generated number is : " + GenaratedNumber);
            int attempt = 0;
            do {
                System.out.print("Guess the Number : ");
                userinput = input.nextInt();
                attempt++;

                if (userinput == GenaratedNumber) {
                    System.out.println("\nCongratulation! You Guessed it Right!");
                    System.out.println("You took " + attempt + " Attempt");
                } else if (userinput < GenaratedNumber)
                    System.out.println("A bit Higher");
                else
                    System.out.println("A bit Lower");

            } while (userinput != GenaratedNumber);

            LowestAttempt.add(attempt);

            System.out.println("1.Start Again\n2.End\n3.View Your Lowest Attempt");
            choice = input.nextInt();

            if (choice == 3) {
                lowattempt = Collections.min(LowestAttempt);
                System.out.println("Your Lowest Attempt is " + lowattempt);
            }
        } while (choice == 1);
    }

    public static void main(String[] args) {
        NumberGuess ng = new NumberGuess();
        System.out.println("\t\t\tWelcome To Number Guessing Game");
        ng.checkResult();
    }
}
