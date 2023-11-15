package org.games.controls;

import javafx.scene.control.TextField;

public class LetterTextField extends TextField {
    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[a-z]") || "".equalsIgnoreCase(text)) {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[a-z]") || "".equalsIgnoreCase(text)) {
            super.replaceSelection(text);
        }
    }
}
