package org.games.hangman;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

class RandomWordFinder implements Closeable {

    private RandomAccessFile file;

    RandomWordFinder() throws FileNotFoundException, URISyntaxException {
        URL url = this.getClass().getResource("/org/games/resources/words.txt");
        System.out.println(url.getPath());
        File f = new File(url.toURI());
        file = new RandomAccessFile(f, "r");
    }

    String findRandomWord() throws IOException {
        // Search a random position in the file
        movePointerToRandomPosition();

        // Search the beginning of the word
        movePointerToBeginningOfWord();

        // Read and return the random word
        return readWord();
    }

    private String readWord() throws IOException {
        String word = "";
        char key;
        while ((key = (char)file.read()) != '*') {
            word += key;
        }
        return word;
    }

    private void movePointerToBeginningOfWord() throws IOException {
        long pos = file.getFilePointer();
        char ch = (char)file.read();

        while (ch != '*') {
            file.seek(--pos);
            ch = (char)file.read();
        }

        // Otherwise it is pointing to a *
        file.seek(pos + 1);
    }

    private void movePointerToRandomPosition() throws IOException {
        long pos;
        char ch;
        do {
            pos = (long) (file.length() * Math.random());
            // Put pointer to random position
            file.seek(pos);
            ch = (char) file.read();
        } while (ch == '*');
    }

    @Override
    public void close() throws IOException {
        file.close();
    }
}
