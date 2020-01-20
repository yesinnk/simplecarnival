package ca.carnivalgames.simplecarnival.fragments.games.slide_down.status;

import android.view.View;
import android.widget.TextView;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.animation.KeyTime;
import ca.carnivalgames.simplecarnival.animation.SimpleKeyFrame;

/**
 * A class that helps manage the animation of display the end of level statuses
 * */
class StatusAnimationManager {

  /**
   * Shows and animates the completed status on screen
   * @param animationEngine The animation engine used in this game
   * @param background The background used to fade and hide the game level
   * @param titleText The title text used to display the title of the status message
   * @param coinText The text used to represent the amount of coins a user has collected
   * @param timeText The text used to represent the amount of time elapsed
   * @param scoreText The text used to represent the total score of a user spanning all games
   */
  void showCompletedStatus(
      AnimationEngine animationEngine,
      final View background,
      final TextView titleText,
      final TextView coinText,
      final TextView timeText,
      final TextView scoreText) {

    final int backgroundFadeAnimationLength = 2;

    animationEngine.addKeyframe(
        "Fade Background",
        backgroundFadeAnimationLength,
        new SimpleKeyFrame(new KeyTime(0, backgroundFadeAnimationLength), true, 0) {
          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            float opacity = covertFrameToSeconds(frameNumber) / backgroundFadeAnimationLength;
            background.setAlpha(opacity);
          }
        });

    animationEngine.addKeyframe(
        "Title Text Animation",
        3,
        new SimpleKeyFrame(new KeyTime(2, 3), false, 0) {
          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            titleText.setVisibility(View.VISIBLE);
          }
        });

    animationEngine.addKeyframe(
        "Coin & Time Text Animation",
        5,
        new SimpleKeyFrame(new KeyTime(3, 4), false, 0) {
          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            coinText.setVisibility(View.VISIBLE);
            timeText.setVisibility(View.VISIBLE);
          }
        });

    animationEngine.addKeyframe(
        "ScoreKeeper Text Animation",
        6,
        new SimpleKeyFrame(new KeyTime(4, 5), false, 0) {
          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            scoreText.setVisibility(View.VISIBLE);
          }
        });
  }
}
