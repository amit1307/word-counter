package com.amit.test.service;

import com.amit.test.Translator;
import com.amit.test.domain.Word;
import com.amit.test.exception.InvalidWordException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isAlpha;

@Component
public class WordCounterService {
    private final Map<Word, Integer> wordToCount = new HashMap<>();
    private final Translator translator;

    public WordCounterService(Translator translator) {
        this.translator = translator;
    }

    public void addWord(String word) {
        if (!isAlpha(word)) {
            throw new InvalidWordException("Invalid word");
        }

        String translatedWord = translate(word);
        Word key = new Word(word, translatedWord);
        boolean containsKey = wordToCount.containsKey(key);
        wordToCount.compute(key, (mapKey, value) -> {
            if (value != null && value != 0) {
                return value + 1;
            } else if (containsKey) {
                int newValue = wordToCount.get(key) + 1;
                wordToCount.put(key, newValue);
                return newValue;
            }
            return 1;
        });
    }

    public long count(String inputWord) {
        if (!isAlpha(inputWord)) return 0;

        Integer count = wordToCount.get(new Word(inputWord, translate(inputWord)));
        return count != null ? count : 0;
    }

    private String translate(String word) {
        return translator.translate(word);
    }
}
