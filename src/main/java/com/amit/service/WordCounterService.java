package com.amit.service;

import com.amit.Translator;
import com.amit.exception.InvalidWordException;
import com.amit.exception.TranslationException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.commons.lang3.StringUtils.isAlpha;

@Component
public class WordCounterService {
    private final Map<String, Integer> wordToCount = new ConcurrentHashMap<>();
    private final Translator translator;

    public WordCounterService(Translator translator) {
        this.translator = translator;
    }

    public void addWord(String word) {
        if (!isAlpha(word)) {
            throw new InvalidWordException("Invalid word");
        }

        String translatedWord = translate(word);
        wordToCount.compute(translatedWord, (mapKey, value) -> {
            int count = 1;
            if (value != null && value != 0) {
                count = value + 1;
            }
            if (!translatedWord.equals(word)) {
                wordToCount.put(word, count);
            }
            return count;
        });
    }

    public long count(String inputWord) {
        int defaultCount = 0;
        if (!isAlpha(inputWord) || !wordToCount.containsKey(inputWord)) return defaultCount;

        Integer wordCount = wordToCount.get(translate(inputWord));
        return wordCount != null ? wordCount : defaultCount;
    }

    private String translate(String word) {
        try {
            return translator.translate(word);
        } catch (Exception exception) {
            throw new TranslationException("Translation failed");
        }
    }
}
