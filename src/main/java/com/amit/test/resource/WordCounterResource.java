package com.amit.test.resource;

import com.amit.test.service.WordCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wordcounter")
public class WordCounterResource {

    @Autowired
    private WordCounterService service;

    @PostMapping("/add/{word}")
    public void addWord(@PathVariable String word) {
        service.addWord(word);
    }

    @GetMapping("/count/{word}")
    public Long count(@PathVariable String word) {
        return service.count(word);
    }
}
