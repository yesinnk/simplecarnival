package ca.carnivalgames.simplecarnival.fragments.games;
/**
 *
 * Class for getting the common game information such as the statistics, the level, the title
 * and the users points
 * The class takes in the title, score, level and points as parameters
 *
 */

public class GameInfo {
  private final String gameTitle;
  private final int userScore;
  private final int userLevel;
  private final int coinsCollect;

  public GameInfo(String gameTitle, int userScore, int userLevel, int coinsCollect) {
    this.gameTitle = gameTitle;
    this.userScore = userScore;
    this.userLevel = userLevel;
    this.coinsCollect = coinsCollect;
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

    @Override
    public String toString() {
        return "GameInfo{" +
                "gameTitle='" + gameTitle + '\'' +
                ", userScore=" + userScore +
                ", userLevel=" + userLevel +
                ", coinsCollect=" + coinsCollect +
                '}';
    }
}
