package ca.carnivalgames.simplecarnival.fragments.games.slide_down.status;

import java.util.ArrayList;

/**
 * A class focused on the management of scores and times across all levels of the game
 * */
public class ScoreKeeper {
  private final ArrayList<Integer> scoresPerLevel;
  private int level;
  private long levelStartTime;
  private long levelEndTime;
  private long extraTime;
  private long totalScore;
  private boolean isPaused;

  public ScoreKeeper() {
    totalScore = 0;
    scoresPerLevel = new ArrayList<>();
    extraTime = 0;
  }

  /**
   * Increases the score for the current level
   * @param i The value amount the score will be increased
   * */
  public void increaseBy(int i) {
    int newScore = scoresPerLevel.get(level) + i;
    scoresPerLevel.set(level, newScore);
  }

  /**
   * Sets the current level that will be used in the calculation of time and scores
   * @param level The current level the user is on
   * */
  public void setLevel(int level) {
    this.level = level;
    scoresPerLevel.add(level, 0);
  }

  /** @return The current score for the current level
   * */
  public int getScore() {
    return level < scoresPerLevel.size()? scoresPerLevel.get(level): 0;
  }

  /**
   * @return The total score across all levels
   * */
  public  long getTotalScore() {
    return totalScore;
  }

  void updateTotalScore(int levelWorstTimeLength){
    int timeDifference = levelWorstTimeLength - getFinalTimeInSeconds();
    totalScore += getScore() * timeDifference;
  }

  /**
   * Stores the start time of a game
   * */
  public void startTimer() {
    if(!isPaused)
    levelStartTime = System.currentTimeMillis();
  }

  /**
   * Stores the time the game ended
   * */
  public void stopTimer() {
    if(!isPaused)
    levelEndTime = System.currentTimeMillis();
  }

  /**
   * Calculates the time the game ended excluding an pauses to the game
   * @return The time the user took to play through the level
   *
   * */
  int getFinalTimeInSeconds() {
    return (int) ((levelEndTime - levelStartTime + extraTime) / 1000);
  }

  /**
   * @return The current time that has elapsed in a level excluding game pauses
   * */
  public int getTimeElapsed() {
    return (int) ((System.currentTimeMillis() - levelStartTime + extraTime) % 1000);
  }
  /**
   * Excludes times elapsed after this method is called
   * */
  public void pauseTimer() {
    isPaused = true;
    extraTime += levelStartTime - System.currentTimeMillis();
  }

  /**
   * Resumes any time that previously elapsed + any new time
   * */
  public void unpauseTimer() {
    isPaused = false;
    startTimer();
  }

  public int getLevel() {
    return level;
  }
}
