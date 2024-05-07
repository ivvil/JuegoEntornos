package org.example.highscore;

import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Score implements Serializable {
    private String name;
    private int score;

    public Score() {
    }

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

    public void sendScore() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8081/set-score");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] postData = ("name=" + name + "&score=" + score).getBytes(StandardCharsets.UTF_8);
                os.write(postData, 0, postData.length);
            }
        } catch (Exception e) {
            System.out.println("Couldn't connect to the database");
            return;
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    @Override
    public String toString() {
        return "Score{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
