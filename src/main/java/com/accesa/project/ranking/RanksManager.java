package com.accesa.project.ranking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RanksManager {
    RanksDAO ranksDAO;

    public RanksManager() throws SQLException {
        this.ranksDAO = new RanksDAO();
    }

    public List<String[]> getRanksList() throws SQLException {
        List<Ranks> ranksList = ranksDAO.getRanking();
        List<String[]> rankingList = new ArrayList<>();

        for (Ranks rank : ranksList) {
            String[] rankList = new String[2];
            rankList[0] = rank.getName();
            rankList[1] = String.valueOf(rank.getScore());
            rankingList.add(rankList);
        }

        return rankingList;
    }

}
