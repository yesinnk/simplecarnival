package ca.carnivalgames.simplecarnival.fragments.settings;

import android.icu.text.SimpleDateFormat;

import java.util.Date;

import ca.carnivalgames.simplecarnival.persistence.GameStatistic;

public class ScoreboardStatistic {
    private final GameStatistic topUser;
    private final GameStatistic currentUser;

    public ScoreboardStatistic(GameStatistic topUser, GameStatistic currentUser) {
        this.topUser = topUser;
        this.currentUser = currentUser;
    }

    String getGameTitle(){
        return topUser.getGameTitle();
    }

    int getTopScore(){
        return topUser.getUserScore();
    }

    String getTopUserName(){
        return topUser.getUserName();
    }

    String getTopUserScoreDate(){
        Date date = new Date(topUser.getDateCreated());
        return new SimpleDateFormat("eee d MMMM ").format(date);
    }

    int getUserScore(){
        return currentUser.getUserScore();
    }

    int getUserLevel(){
        return currentUser.getUserLevel();
    }

    int getUserCoin(){
        return currentUser.getCoinsCollect();
    }
}
