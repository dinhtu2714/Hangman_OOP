package org.games.hangman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GameController {

    private static final int MAX_WRONG_GUESSES = 7;

    private RandomWordFinder rndWordFinder;
    private String rndWord;

    private List<Character> enteredChars;
    private int wrongGuesses;

    GameController() {

        enteredChars = new ArrayList<>();
        wrongGuesses = 0;
        try {
            rndWordFinder = new RandomWordFinder();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setNewRandomWord();
    }

    String getCurrentWord() {
        String currentWord = "";
        for (char c : rndWord.toCharArray()) {
            if (enteredChars.contains(c)) {
                currentWord += c + " ";
            } else {
                currentWord += "_ ";
            }
        }
        return currentWord;
    }

    String getMissingChars() {
        String missingChars = "";
        for (char c : rndWord.toCharArray()) {
            if (enteredChars.contains(c)) {
                missingChars += "  ";
            } else {
                missingChars += c + " ";
            }
        }
        return missingChars;
    }

    String getWord() {
        String word = "";
        for (char c : rndWord.toCharArray()) {
            word += c + " ";
        }
        return word;
    }

    void reset() {
        wrongGuesses = 0;
        enteredChars.clear();
        setNewRandomWord();
    }

    private void setNewRandomWord() {
        try {
            rndWord = rndWordFinder.findRandomWord();
            System.out.println("Random word: " + rndWord);
            System.out.println("Word length: " + rndWord.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int getWrongGuesses() {
        return wrongGuesses;
    }

    boolean addChar(char ch) {
        boolean wrongGuess = false;
        if ((!enteredChars.stream().anyMatch(i -> i.equals(ch)))) {
            enteredChars.add(ch);

            if (!rndWord.contains(String.valueOf(ch))) {
                wrongGuess = true;
                wrongGuesses++;
            }
        }

        return wrongGuess;
    }

    List<Character> getEnteredChars() {
        return Collections.unmodifiableList(enteredChars);
    }

    boolean isGameOver() {
        return wrongGuesses >= MAX_WRONG_GUESSES;
    }

    boolean isGameWon() {
        for (char c : rndWord.toCharArray()) {
            if (!enteredChars.contains(c)) {
                return false;
            }
        }

        return true;
    }
}
