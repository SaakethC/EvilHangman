import com.sun.source.tree.Tree;

import java.io.*;
import java.util.*;

public class EvilHangman
{
   private static ArrayList<String> allWords = new ArrayList<>();
   private static TreeMap<String, ArrayList<String>> familyMap = new TreeMap<>();

   //this method will return the "--e-"
   //family that the word belongs to.  Used in building 
   //the map.
   public static String getFamily(String word, char c)
   {
      String output = "";
      for (char ch : word.toUpperCase().toCharArray()) {
         if (ch != c) {
            output += "-";
         } else {
            output += c;
         }
      }
      return output;
   }
   public static void main(String[] args) throws Exception
   {
      //step 1
      Scanner File = new Scanner(new File("dictionary.txt"));
      //step 2
      Scanner kb = new Scanner(System.in);
      boolean gotLength = false;
      int wordLen = 0;
      while (gotLength == false) {
         try {
            System.out.print("Enter word length: ");
            wordLen = kb.nextInt();
            if (wordLen < 1 || wordLen == 26 || wordLen == 27 || wordLen == 23 || wordLen > 29) {
               System.out.println("Invalid input, try again please");
            } else {
               gotLength = true;
               System.out.println("[SYSTEM] set word length to " + wordLen + " letters");
            }
         } catch (Exception e) {
            System.out.println("Invalid input, try again please");
            kb.nextLine();
         }
      }
      //step 3
      boolean gotGuesses = false;
      int guessLen = 0;
      while (gotGuesses == false) {
         try {
            System.out.print("Enter number of guesses: ");
            guessLen = kb.nextInt();
            if (guessLen < 1) {
               System.out.println("Invalid input, try again please");
            } else {
               gotGuesses = true;
               System.out.println("[SYSTEM] set number of guesses to " + guessLen);
            }
         } catch (Exception e) {
            System.out.println("Invalid input, try again please");
            kb.nextLine();
         }
      }
      //step 4
      boolean getMode = false;
      boolean runningTotal = false;
      kb.nextLine();
      while (getMode == false) {
         try {
            System.out.print("Would you like a running total of the number of words left? ");
            String input = kb.next().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
               getMode = true;
               runningTotal = true;
               System.out.println("[SYSTEM] enabled running total of words remaining");
            } else if (input.equals("no") || input.equals("n")) {
               getMode = true;
               runningTotal = false;
               System.out.println("[SYSTEM] disabled running total of words remaining");
            } else {
               System.out.println("Invalid input, try again please");
            }
         } catch (Exception e) {
            System.out.println("Invalid input, try again please");
            kb.nextLine();
         }
      }
      //step 5
      //5.1
      while (File.hasNextLine()) {
         String input = File.nextLine();
         if (input.length() == wordLen) {
            allWords.add(input.toUpperCase());
         }
      }
      //5.2
      ArrayList<String> guessedLetters = new ArrayList<String>();
      kb.nextLine();
      //5.3
      while(true) {
         System.out.println("\nYou have " + guessLen + " guesses remaining");
         if (guessedLetters.size() == 0) {
            System.out.println("You have not yet guessed any letters");
         } else {
            System.out.print("You have already guessed the letters ");
            for (String x : guessedLetters) {
               System.out.print(x + " ");
            }
            System.out.print("\n");
         }
         if (runningTotal) {
            System.out.println("You have " + allWords.size() + " words remaining");
         }
         boolean gotGuess = false;
         String guess = "";
         while (!gotGuess) {
            System.out.print("Enter your guess: ");
            guess = kb.nextLine().toUpperCase();
            if (guess.length() == 1 && guess.charAt(0) >= 65 && guess.charAt(0) <= 90 && !guessedLetters.contains(guess)) {
               gotGuess = true;
            } else {
               System.out.println("Invalid input, try again please");
            }
         }
         guessedLetters.add(guess);
         Collections.sort(guessedLetters);
         //5.4
         for (String word : allWords) {
            String family = getFamily(word, guess.charAt(0));
            if (familyMap.containsKey(family)) {
               ArrayList<String> temp = familyMap.get(family);
               temp.add(word);
               familyMap.put(family, temp);
            } else {
               ArrayList<String> temp = new ArrayList<>();
               temp.add(word);
               familyMap.put(family, temp);
            }
         }
         //5.5
         int max = 0;
         String maxFamily = "";
         for (String k : familyMap.keySet()) {
            if (familyMap.get(k).size() > max) {
               max = familyMap.get(k).size();
               maxFamily = k;
            }
         }
         boolean blankFamily = true;
         for (char c : maxFamily.toCharArray()) {
            if (c != '-')
               blankFamily = false;
         }
         if (blankFamily) {
            guessLen--;
            System.out.println("That letter is not in the word");
         }
         for (int i = 0; i < allWords.size(); i++) {
            if (familyMap.get(maxFamily).contains(allWords.get(i)) == false) {
               allWords.remove(i);
               i--;
            }
         }
         String output = "";
         for (char c : familyMap.get(maxFamily).get(0).toCharArray()) {
            if (guessedLetters.contains(c+"")) {
               output += c;
               blankFamily = false;
            } else {
               output += "-";
            }
         }
         output = output.toUpperCase();
         System.out.println(output);
         //5.6
         boolean gameEnded = false;
         if (guessLen == 0) {
            System.out.println("\nYou lost!");
            gameEnded = true;
         } else if (!output.contains("-")) {
            System.out.println("\nYou won!");
            gameEnded = true;
         }
         if (gameEnded) {
            boolean getRestart = false;
            while (getRestart == false) {
               System.out.print("Would you like to play again? ");
               String input = kb.next().toLowerCase();
               if (input.equals("yes") || input.equals("y")) {
                  getRestart = true;
                  System.out.println("Restarting...\n");
                  String[] str = new String[0];
                  main(str);
               } else if (input.equals("no") || input.equals("n")) {
                  getRestart = true;
                  System.out.println("Ending...");
                  System.exit(0);
               } else {
                  System.out.println("Invalid input, try again please");
               }
            }
         }
         familyMap = new TreeMap<>();
      }
   }
}