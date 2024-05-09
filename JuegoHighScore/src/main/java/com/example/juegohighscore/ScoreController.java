package com.example.juegohighscore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScoreController {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @GetMapping("/get-score")
    public Score getScore(@RequestParam("name") String name) {
        return scoreRepository.findById(name).orElse(null);
    }

    @PostMapping("/set-score")
    public void setScore(@RequestParam("name") String name, @RequestParam("score") int score) {
        Score newScore = new Score();
        newScore.setName(name);
        newScore.setScore(score);
        scoreRepository.save(newScore);
    }

    @DeleteMapping("/delete-score")
    public void deleteScore() {
        scoreRepository.deleteAll();
    }

    @GetMapping("/get-highscore-table")
    public List<Score> getHighscoreTable() {
        return scoreRepository.findTop10rderByScoreDesc();
    }
}