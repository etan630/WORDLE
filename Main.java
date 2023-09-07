import java.util.*;
import java.util.Scanner;

public class Main {
  // Color constants
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_CYAN = "\u001b[36;1m";
  public static final String ANSI_GREEN = "\u001b[42;1m";
  public static final String ANSI_YELLOW = "\u001b[43;1m";
  public static final String ANSI_RED = "\u001b[41;1m";
  public static final String ANSI_MAGENTA = "\u001b[35m";
  public static final String ANSI_BG_CYAN = "\u001b[46;1m";

  public static void main(String[] args) {
    // Choosing the wordle answer
    String[] strArr = new String[] {
      "goose", "adult", "chain", "earth", "front", "level", "money", "phase", "youth", "truth", "water", "ahead",
      "album", "apple", "anger", "aside", "built", "catch", "chose", "clear", "drive", "fault", "found"
    };
    int arrCount = strArr.length; // Simplify counting the array length
    int num = (int) (Math.random() * arrCount); // Random index for word selection
    String wordleAnswer = strArr[num];

    // Rules
    printRules();

    // Ask if ready to start the game
    Scanner input = new Scanner(System.in);
    System.out.print("\nAre you ready to play? Type yes or no: ");
    String answer = input.nextLine();

    // Stop if player says no
    if (answer.equals("no")) {
      printMiniGame();
    } else {
      playWordleGame(wordleAnswer, input);
    }
  }

  // Function to print the game rules
  private static void printRules() {
    System.out.println(ANSI_CYAN + "WELCOME TO WORDLE" + ANSI_RESET);
    System.out.println("Rules: Guess the 5 letter word in 6 tries");
    System.out.println("Only use lowercase letters");
    System.out.println(ANSI_GREEN + "Green: This means the letter and placement is correct" + ANSI_RESET);
    System.out.println(ANSI_YELLOW + "Yellow: The letter is correct but in the wrong placement" + ANSI_RESET);
    System.out.println(ANSI_RED + "Red: The letter is not in the word" + ANSI_RESET);
  }

  // Function to play the mini-game if player says no
  private static void printMiniGame() {
    System.out.println("\nCome play next time!");
    System.out.println("...");
    System.out.println("But since you don't want to play, let's do a small mini game heheh");
    System.out.println("");
    System.out.println("Look for the mistake:");
    System.out.println(ANSI_BG_CYAN + "mmmmmmmmmm");
    System.out.println("mmmmmmmmmm");
    System.out.println("mmmmmmmmmm");
    System.out.println("mmmmmnmmmm" + ANSI_RESET);
    System.out.println("");
    System.out.println("Type the letter, row, and then column. For example, i, 3, 6");
    System.out.println("");
    Scanner input = new Scanner(System.in);
    String mistakeLetter = input.nextLine();
    System.out.println("");

    if (mistakeLetter.equals("n, 4, 6")) {
      System.out.println(ANSI_CYAN + "You got it... ok you can go now bye");
    } else {
      System.out.println("whoops you didn't get it");
    }
  }

  // Function to play the Wordle game
  private static void playWordleGame(String wordleAnswer, Scanner input) {
    String word = null;
    List<String> wrongLetters = new ArrayList<>();
    List<String> rightLetters = new ArrayList<>();

    for (int i = 1; i <= 6; i++) {
      System.out.println("\n" + ANSI_CYAN + "Let's start attempt " + i + "!" + ANSI_RESET);
      word = input.nextLine();

      if (word.length() != 5) {
        handleInvalidWordInput(word);
        i--;
        continue;
      }

      if (word.equals(wordleAnswer)) {
        System.out.println(ANSI_CYAN + "You got the word!" + ANSI_RESET);
        break;
      } else if (i == 6) {
        System.out.println(ANSI_RED + "You didn't get it! The answer was " + wordleAnswer + ANSI_RESET);
      }

      processWord(word, wordleAnswer, wrongLetters, rightLetters);
    }
  }

  // Function to handle invalid word input
  private static void handleInvalidWordInput(String word) {
    System.out.println("Invalid input! Please enter a 5-letter word.");
  }

  // Function to process the player's word and provide feedback
  private static void processWord(String word, String wordleAnswer, List<String> wrongLetters, List<String> rightLetters) {
    for (int x = 0; x < 5; x++) {
      String temp = word.substring(x, x + 1);

      if (word.charAt(x) == wordleAnswer.charAt(x)) {
        System.out.print(ANSI_GREEN + temp + ANSI_RESET);
        rightLetters.add(temp);
      } else {
        boolean letterFound = false;
        for (int y = 0; y < 5; y++) {
          if (word.charAt(x) == wordleAnswer.charAt(y)) {
            letterFound = true;
            break;
          }
        }
        if (letterFound) {
          System.out.print(ANSI_YELLOW + temp + ANSI_RESET);
          rightLetters.add(temp);
        } else {
          System.out.print(ANSI_RED + temp + ANSI_RESET);
          wrongLetters.add(temp);
        }
      }
    }

    printUsedLetters(wrongLetters, rightLetters);
  }

  // Function to print used letters
  private static void printUsedLetters(List<String> wrongLetters, List<String> rightLetters) {
    System.out.println("\n" + ANSI_MAGENTA + "Wrong letters used so far: " + new HashSet<>(wrongLetters) + ANSI_RESET);
    System.out.println("Right letters used so far: " + new HashSet<>(rightLetters));
  }
}
