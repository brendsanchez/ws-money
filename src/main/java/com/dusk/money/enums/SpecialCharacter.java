package com.dusk.money.enums;

public enum SpecialCharacter {
    MONEY('$');

    private final char character;

    SpecialCharacter(char character) {
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }
}