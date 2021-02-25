import java.io.*;
import java.util.*;

public class EvilHangman
{
   private static ArrayList<String> allWords = new ArrayList<>();

   //this method will return the "--e-"
   //family that the word belongs to.  Used in building 
   //the map.
   public static String getFamily(String word, char c)
   {
      return null;
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
         System.out.print("Enter word length: ");
         wordLen = kb.nextInt();
         if (wordLen<1 || wordLen == 26 || wordLen == 27 || wordLen==23 || wordLen>29) {
            System.out.println("Invalid input, try again please");
         } else {
            gotLength = true;
            System.out.println("[SYSTEM] set word length to " + wordLen + " letters");
         }
      }
      //step 3
      boolean gotGuesses = false;
      int guessLen = 0;
      while (gotGuesses == false) {
         System.out.print("Enter number of guesses: ");
         guessLen = kb.nextInt();
         if (guessLen<1) {
            System.out.println("Invalid input, try again please");
         } else {
            gotGuesses = true;
            System.out.println("[SYSTEM] set number of guesses to " + guessLen);
         }
      }

      //step 4
      boolean getMode = false;
      boolean runningTotal = false;
      kb.nextLine();
      while (getMode == false) {
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
      }

      //step 5.1
      while (File.hasNextLine()) {
         String input = File.nextLine();
         if (input.length() == wordLen) {
            allWords.add(input);
         } else {
            System.out.println(input.length());
         }
      }
      System.out.println(allWords.size());
   }
}