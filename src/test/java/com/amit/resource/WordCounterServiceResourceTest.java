package com.amit.resource;

import com.amit.service.WordCounterService;
import com.amit.exception.InvalidWordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WordCounterServiceResourceTest {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @MockBean
    private WordCounterService service;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void shouldRespondOk_whenAddingValidWord() throws Exception {
        // when & then
        this.mockMvc
                .perform(post("/wordcounter/add/hello"))
                .andExpect(status().isOk());

        verify(service).addWord("hello");
    }

    @Test
    public void shouldRespondWithError_whenAddingInvalidWord() throws Exception {
        // given
        String expectedContent = "{\"errorCode\":\"WC_00001\",\"description\":\"Bad Format: Invalid word\"}";
        doThrow(new InvalidWordException("Invalid word")).when(service).addWord("hello1234");

        // when & then
        this.mockMvc
                .perform(post("/wordcounter/add/hello1234"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedContent));

        verify(service).addWord("hello1234");
    }

    @Test
    public void shouldRespondWithCount_whenValidWordCountRequested() throws Exception {
        // given
        String expectedContent = "1";
        when(service.count("hello")).thenReturn(1L);

        // when & then
        this.mockMvc
                .perform(get("/wordcounter/count/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

        verify(service).count("hello");
    }

    @Test
    public void shouldRespondZeroCount_whenInvalidWordCountRequested() throws Exception {
        // given
        String expectedContent = "0";
        when(service.count("hello1234")).thenReturn(0L);

        // when & then
        this.mockMvc
                .perform(get("/wordcounter/count/hello1234"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));

        verify(service).count("hello1234");
    }
}