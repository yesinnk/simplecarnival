package ca.carnivalgames.simplecarnival.fragments.games.slide_down.status;

import android.view.View;
import android.widget.TextView;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.animation.AnimationEngine;

/** Helps organize the status menus that helps display games scores */
public class StatusViewManager {
  private final StatusAnimationManager animator;
  private final View background;
  private final TextView statusText;
  private final TextView coinText;
  private final TextView timeText;
  private final TextView scoreText;

  public StatusViewManager(View view) {
    animator = new StatusAnimationManager();
    statusText = view.findViewById(R.id.slide_title_text);
    coinText = view.findViewById(R.id.slide_coins_status_text);
    timeText = view.findViewById(R.id.slide_time_status_text);
    scoreText = view.findViewById(R.id.slide_score_status_text);
    background = view.findViewById(R.id.black_background);
  }

  /**
   * Shows the status menu which displays the user scores such as coins collected and Time elapsed.
   * Helps animated the views when loaded
   *
   * @param animationEngine The animations engine given by the main fragment of the game that aids
   *     in element animations
   * @param score The ScoreKeeper object that helps keep track of scores
   * @param levelWorstTime The worst time that can possible be attained in a level as set by the
   *     level designer. This help calculate how well a user has done with regards to time
   */
  public void showCompletedStatus(
          AnimationEngine animationEngine, ScoreKeeper score, int levelWorstTime) {
    statusText.setText(R.string.course_cleared_title);

    score.updateTotalScore(levelWorstTime);

    String coinString = "Coins = " + score.getScore();
    String timeString = "Time = " + score.getFinalTimeInSeconds();
    String scoreString = "Score = " + score.getTotalScore();

    coinText.setText(coinString);
    timeText.setText(timeString);
    scoreText.setText(scoreString);

    animator.showCompletedStatus(
        animationEngine, background, statusText, coinText, timeText, scoreText);
  }

  /** Hides all of the status info displayed on the screen */
  public void hideCompletedStatus() {
    background.setAlpha(0);
    statusText.setVisibility(View.GONE);
    coinText.setVisibility(View.GONE);
    timeText.setVisibility(View.GONE);
    scoreText.setVisibility(View.GONE);
  }
}
