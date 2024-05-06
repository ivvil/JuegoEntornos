package com.example.juegohighscore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Score {
    @Id
    private String name;

    private int score;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
