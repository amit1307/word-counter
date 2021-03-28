package com.amit.test.domain;

import java.util.Objects;

public class Word {
    private final String originalWord;
    private final String englishTranslatedWord;

    public Word(String originalWord, String englishTranslatedWord) {
        this.originalWord = originalWord;
        this.englishTranslatedWord = englishTranslatedWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return Objects.equals(englishTranslatedWord, word.englishTranslatedWord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishTranslatedWord);
    }
}
