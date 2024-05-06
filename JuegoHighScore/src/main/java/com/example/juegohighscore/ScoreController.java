package com.example.juegohighscore;

import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreController {
    @GetMapping("/get-score")
    public Score getScore(@BindParam("name") String name) {
        Score score = new Score();
        score.setName(name);
        score.setScore(1000);
        return score;
    }
}
