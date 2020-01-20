package ca.carnivalgames.simplecarnival.fragments.games.create_a_shape;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.animation.KeyTime;
import ca.carnivalgames.simplecarnival.animation.SimpleKeyFrame;

public class CatchAShapeViewModel extends ViewModel {

  // Size
  private int screenWidth;
  private int screenHeight;
  //Labels
  private TextView scoreLabel;
  private TextView missedLabel;
  private TextView startLabel;
  //Score and missed
  private int score;
  private int missed;

  private boolean startFlag;

  //Animation length (decreases as level increases)
  private int animationDuration = 4;
  private int level;
  /** Initialize screen width and height as well as the cup and coin speed dependent on them */
  void setScreenSize(int width, int height) {
    screenWidth = width;
    screenHeight = height;
  }
  /**
   * @param animationEngine The animation engine to control animation of the ball
   * @param coinView the Coin to be animated falling
   */
  void setCoinAnimation(
      final AnimationEngine animationEngine,
      final ImageView coinView,
      final ImageView cupView,
      final int coinX,
      final String animationName) {

    coinView.setTranslationX(coinX);
    animationEngine.addKeyframe(
        animationName,
        animationDuration,
        new SimpleKeyFrame(new KeyTime(0, animationDuration), true) {
          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            int maxBallPosition = screenHeight;
            float position = ((float) frameNumber / (animationDuration * 60)) * maxBallPosition;
            coinView.setTranslationY(position);
            //If collision, update labels and increase score
            if (hasCollided(coinView, cupView)) {
              score++;
              updateState(animationEngine,coinView,cupView,animationName);
              scoreLabel.setText("SCORE: " + score);
            }
            //If missed, update labels and increase missed
            if (coinView.getY() > cupView.getY() + cupView.getHeight()){
              missed++;
              updateState(animationEngine,coinView,cupView,animationName);
              missedLabel.setText("MISSED: " + missed);
            }
          }
        });
  }
  /**
   * Function that determines the current state of the game that is called when a coin is missed or
   * collected. If a coin is collected, the function decides whether to progress to the next level.
   * If a coin is missed the function decides whether the game is over and to reset the local vars.
   */
  private void updateState(AnimationEngine animationEngine,
                           ImageView coinView, ImageView cupView, String animationName){
    if(score % 5 == 0 && score > 0 && animationDuration != 1){
      animationDuration -= 1;
      level++;

    }
    else if(missed == 10){
      reset(coinView, cupView);
      animationEngine.removeKeyframe(animationName);
      return;
    }
    //Reset the animation
    animationEngine.removeKeyframe(animationName);
    setCoinAnimation(animationEngine, coinView, cupView,
            (int) Math.floor(Math.random() * (screenWidth - coinView.getWidth())),animationName);
  }

  /**
   * Resets game state and local variables to their initial states before the game starts
   */
  public void reset(ImageView coin, ImageView cup){
    score = 0;
    missed = 0;
    scoreLabel.setText("SCORE: "+ score);
    animationDuration = 4;
    startLabel.setVisibility(View.VISIBLE);
    cup.setX(screenWidth/2);
    coin.setY(-100);
    startFlag = false;
  }

  public void start(){
    startFlag = true;
    startLabel.setVisibility(View.INVISIBLE);
  }

  // instead of checkCollision try using boolean method hasCollided
  // Do scoring here
  private boolean hasCollided(ImageView coin, ImageView cup) {
    if (coin.getY() < cup.getY()) return false;

    float coinXPosition = coin.getX();
    float cupX1 = cup.getX();
    float cupX2 = cupX1 + cup.getWidth();

    return cupX1 <= coinXPosition && coinXPosition <= cupX2;
  }

  public void setLabels(TextView score, TextView miss, TextView start){
    scoreLabel = score;
    missedLabel = miss;
    startLabel = start;
  }

  public boolean isStarted(){
    return startFlag;
  }


  public int getScore(){
    return score;
  }

  public int getLevel(){
    return level;
  }
}
