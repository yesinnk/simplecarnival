package ca.carnivalgames.simplecarnival.persistence;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Stats {
  @PrimaryKey @NonNull public final String game;

  public int userId;
  public int score;

  public Stats(String game, int userId, int score) {
    this.game = game;
    this.userId = userId;
    this.score = score;
  }

  public String getGame() {
    return game;
  }

  public int getUserId() {
    return userId;
  }

  public int getScore() {
    return score;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
