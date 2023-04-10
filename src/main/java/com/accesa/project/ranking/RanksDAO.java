package com.accesa.project.ranking;

import com.accesa.project.database.DBConnections;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RanksDAO {

    private final Connection connection;

    public RanksDAO() throws SQLException {
        DBConnections DBConnection = new DBConnections();
        connection = DBConnections.databaseConnection();
    }

    public List<Ranks> getRanking() {
        List<Ranks> ranks = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ranking_name, ranking_score FROM qHub_orderedRankings");

            while (resultSet.next()) {
                String name = resultSet.getString("ranking_name");
                int score = resultSet.getInt("ranking_score");
                Ranks r = new Ranks(name, score);
                ranks.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ranks;
    }
}
