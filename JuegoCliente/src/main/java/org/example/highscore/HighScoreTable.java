package org.example.highscore;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HighScoreTable {
    public static DefaultTableModel getHighScoreTableModel() {
        String[] columnNames = {"Name", "Score"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        try {
            List<Score> highScoreTable = getHighScoreTable();
            if (highScoreTable == null) 
                return null;
                
            for (Score score : highScoreTable) {
                model.addRow(new Object[]{score.getName(), score.getScore()});
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al obtener las puntuaciones");
        }
        return model;
    }

    public static String getHighScoreString() {
        String inputLine = null;
        BufferedReader in = null;
        StringBuffer content = null;
        HttpURLConnection connection = null;

        try {
            URL url = new URL("http://vps.mariol03.es:8081/get-highscore-table");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (in != null) in.close();
                if (connection != null) connection.disconnect();
            } catch (IOException e) {
                return null;
            }
        }
        return content.toString();
    }


    public static List<Score> getHighScoreTable() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String highScoreString = getHighScoreString();
        if (highScoreString != null)
            return objectMapper.readValue(highScoreString, new TypeReference<List<Score>>() {});
        else
            return null;
    }
}
