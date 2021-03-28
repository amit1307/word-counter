package com.amit;

import com.amit.service.WordCounterService;
import com.amit.exception.InvalidWordException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordCounterServiceTest {

    private static final String SOME_INVALID_WORD = "hello123";
    private static final String SOME_VALID_WORD = "hello";

    @Mock
    private Translator translator;

    @InjectMocks
    private WordCounterService wordCounterService;

    @Test
    void shouldAddAValidWord() {
        // when
        wordCounterService.addWord(SOME_VALID_WORD);
        long count = wordCounterService.count(SOME_VALID_WORD);

        // then
        assertEquals(1, count);
        verify(translator, times(2)).translate(anyString());
    }

    @Test
    void shouldGiveCount_whenSameWordIsAddedMultipleTimes() {
        // when
        wordCounterService.addWord(SOME_VALID_WORD);
        wordCounterService.addWord(SOME_VALID_WORD);
        wordCounterService.addWord(SOME_VALID_WORD);
        long count = wordCounterService.count(SOME_VALID_WORD);

        // then
        assertEquals(3, count);
        verify(translator, times(4)).translate(anyString());
    }

    @Test
    void shouldThrowException_whenInvalidWordAdded() {
        // when
        InvalidWordException exception = assertThrows(InvalidWordException.class, () -> wordCounterService.addWord(SOME_INVALID_WORD));

        // then
        assertEquals(exception.getMessage(), "Invalid word");
        verifyNoMoreInteractions(translator);
    }

    @Test
    void shouldGiveCount_whenSameWordTranslatedInDifferentLanguageAdded_andCountAskedForEnglishWord() {
        // given
        when(translator.translate("flor")).thenReturn("flower");
        when(translator.translate("blume")).thenReturn("flower");
        when(translator.translate("flower")).thenReturn("flower");
        wordCounterService.addWord("flower");
        wordCounterService.addWord("flor");
        wordCounterService.addWord("blume");

        // when
        long count = wordCounterService.count("flower");

        // then
        assertEquals(3, count);
        verify(translator, times(4)).translate(anyString());
    }

    @Test
    void shouldGiveCount_whenSameWordTranslatedInDifferentLanguageAdded_andCountAskedForNonEnglishWord() {
        // given
        when(translator.translate("flor")).thenReturn("flower");
        when(translator.translate("blume")).thenReturn("flower");
        when(translator.translate("flower")).thenReturn("flower");
        wordCounterService.addWord("flower");
        wordCounterService.addWord("flor");
        wordCounterService.addWord("blume");

        // when
        long count = wordCounterService.count("blume");

        // then
        assertEquals(3, count);
        verify(translator, times(4)).translate(anyString());
    }

    @Test
    void shouldGiveZeroCount_forInvalidWord() {
        // when
        long count = wordCounterService.count(SOME_INVALID_WORD);

        // then
        assertEquals(0, count);
        verifyNoInteractions(translator);
    }

    @Test
    void shouldGiveZeroCount_whenWordNotFound() {
        // given
        when(translator.translate(SOME_VALID_WORD)).thenReturn(SOME_VALID_WORD);
        when(translator.translate("hi")).thenReturn("hi");
        wordCounterService.addWord(SOME_VALID_WORD);

        // when
        long countFromMap = wordCounterService.count("hi");

        // then
        assertEquals(0, countFromMap);
        verify(translator, times(2)).translate(anyString());
    }
}