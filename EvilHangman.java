import java.io.*;
import java.util.*;

public class EvilHangman
{
   //this method will return the "--e-"
   //family that the word belongs to.  Used in building 
   //the map.
	public static String getFamily(String word, char c)
   {
      return " ";
   }	
   public static void main(String[] args) throws FileNotFoundException
   {
      int wordLength = 0;
      int guessTotal = 0;
      Scanner file = new Scanner(new File("dictionary.txt"));
      List<String> all = new ArrayList<String>();
      while(file.hasNext()){
          String line = file.nextLine();
          line.toLowerCase();
          all.add(line);
      }

      Scanner userInput = new Scanner(System.in);
      System.out.println("Enter Word Length:");
      wordLength = Integer.parseInt(userInput.nextLine());
      System.out.println("Enter Number Of Guesses:");
      guessTotal = Integer.parseInt(userInput.nextLine());
      System.out.println(guessTotal + " " + wordLength + " " + all);
   }
}