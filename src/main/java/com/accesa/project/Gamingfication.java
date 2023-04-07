package com.accesa.project;

import com.accesa.project.main.Quests;
import com.accesa.project.ranking.Ranking;
import com.accesa.project.main.RewardsCatalogue;
import com.accesa.project.user.User;

import java.util.List;

public class Gamingfication {
    private List<User> users;
    private List<Quests> quests;
    private Ranking ranking;
    private RewardsCatalogue rewardsCatalog;

    public void addUser(User user) {
        // add a new user to the application
    }

    public User authenticateUser(String email, String password) {
        // authenticate a user based on their email and password
        return null;
    }

    public void addQuest(Quests quest) {
        // add a new quest to the application
    }

    public void solveQuest(User user, Quests quest) {
        // mark the quest as completed by the user and update their tokens and badges
    }

    public void updateRanking() {
        // update the ranking of users based on their tokens or badges
    }

    public void addReward(String name, int cost) {
        // add a new reward to the catalog
    }

    public void redeemReward(User user, String rewardName) {
        // allow the user to redeem the reward if they have enough tokens/badges
    }

    // getters and setters
}
