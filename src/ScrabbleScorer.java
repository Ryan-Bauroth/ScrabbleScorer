import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class for simple scrabble scorer
 * @Author Ryfi
 * @Version 1.30.23
 */
public class ScrabbleScorer {
    String[][] pointValues = {{"A", "1"},{"B", "3"},{"C", "3"},{"D", "2"},{"E", "1"},{"F", "4"},{"G", "2"},{"H", "4"},{"I", "1"},{"J", "8"},{"K", "5"},{"L", "1"},{"M", "3"},{"N", "1"},{"O", "1"},{"P", "3"},{"Q", "10"},{"R", "1"},{"S", "1"},{"T", "1"},{"U", "1"},{"V", "4"},{"W", "4"},{"X", "8"},{"Y", "4"},{"Z", "10"}};
    ArrayList<ArrayList<String>> dict;

    /**
     * Creates arraylists to sort words
     */
    public ScrabbleScorer(){
        dict = new ArrayList<>();
        for(int i = 0; i < 26; i++){
            dict.add(new ArrayList<>());
        }
    }

    /**
     * Adds words from file to arraylists
     * @throws FileNotFoundException
     */
    public void buildDictionary() throws FileNotFoundException {
        String filename = "SCRABBLE_WORDS.txt";
        Scanner in = new Scanner(new File(filename));
        while(in.hasNext()) {
            String isbn = in.nextLine();
            for(int i = 0; i < 26; i++){
                if(isbn.substring(0, 1).equals(pointValues[i][0])){
                    dict.get(i).add(isbn);
                }
            }
        }
        in.close();
        for (ArrayList<String> strings : dict) {
            Collections.sort(strings);
        }
    }

    /**
     * Method to validate words
     * @param word
     * @return true if word is valid false if otherwise
     */
    public boolean isValidWord(String word){
        for(int i = 0; i < 26; i++){
            if(word.substring(0, 1).equals(pointValues[i][0])){
                if(!(Collections.binarySearch(dict.get(i), word) < 0)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to score scrabble words
     * @param word
     * @return score of the word
     */
    public int getWordScore(String word){
        int score = 0;
        for(int i = 0; i < word.length(); i++){
            for (String[] pointValue : pointValues) {
                if (pointValue[0].equals(word.substring(i, i + 1))) {
                    score += Integer.parseInt(pointValue[1]);
                }
            }
        }
        return score;
    }

    /**
     * Main method of class ScrabbleScorer
     * @param args as needed
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        ScrabbleScorer app = new ScrabbleScorer();
        app.buildDictionary();
        Scanner in = new Scanner(System.in);
        outerloop:
        while(true) {
            System.out.print("* Welcome to the Scrabble Word Scorer app *\nEnter a word to score or 0 to quit: ");
            String input = in.nextLine().toUpperCase(Locale.ROOT);
            if (input.equals("0"))
                break outerloop;
            else if (app.isValidWord(input.trim())) {
                System.out.println(input.trim() + " = " + app.getWordScore(input.trim()) + " points");
            } else {
                System.out.println(input.trim() + " is not a valid word in the dictionary");
            }
        }
        System.out.println("Exiting the program thanks for playing");
    }
}
