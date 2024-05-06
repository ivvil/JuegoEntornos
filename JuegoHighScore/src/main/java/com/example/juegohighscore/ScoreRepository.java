package com.example.juegohighscore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, String>{
    @Query(value = "SELECT s.* FROM Score s ORDER BY s.score DESC", nativeQuery = true)
    List<Score> findAllOrderByScoreDesc();

    @Query(value = "SELECT s.* FROM Score s order by s.score desc LIMIT 10", nativeQuery = true)
    List<Score> findTop10rderByScoreDesc();
}
