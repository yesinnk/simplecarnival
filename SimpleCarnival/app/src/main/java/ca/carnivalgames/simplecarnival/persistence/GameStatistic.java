package ca.carnivalgames.simplecarnival.persistence;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("gameTitle")})
public class GameStatistic {
  @PrimaryKey @NonNull private String userGamerId;
  private final String userName;
  private final String gameTitle;
  private final int userScore;
  private final int userLevel;
  private final int coinsCollect;
  private final long dateCreated;

  public GameStatistic(
      @NonNull String userGamerId,
      String userName,
      String gameTitle,
      int userScore,
      int userLevel,
      int coinsCollect,
      long dateCreated) {
    this.userGamerId = userGamerId;
    this.userName = userName;
    this.gameTitle = gameTitle;
    this.userScore = userScore;
    this.userLevel = userLevel;
    this.coinsCollect = coinsCollect;
    this.dateCreated = dateCreated;
  }

  @NonNull
  public String getUserGamerId() {
    return userGamerId;
  }

  public String getUserName() {
    return userName;
  }

  public String getGameTitle() {
    return gameTitle;
  }

  public int getUserScore() {
    return userScore;
  }

  public int getUserLevel() {
    return userLevel;
  }

  public int getCoinsCollect() {
    return coinsCollect;
  }

  public long getDateCreated() {
    return dateCreated;
  }

  @Override
  public String toString() {
    return "GameStatistic{"
        + "userGamerId='"
        + userGamerId
        + '\''
        + ", userName='"
        + userName
        + '\''
        + ", gameTitle='"
        + gameTitle
        + '\''
        + ", userScore="
        + userScore
        + ", userLevel="
        + userLevel
        + ", coinsCollect="
        + coinsCollect
        + ", dateCreated="
        + dateCreated
        + '}';
  }
}
