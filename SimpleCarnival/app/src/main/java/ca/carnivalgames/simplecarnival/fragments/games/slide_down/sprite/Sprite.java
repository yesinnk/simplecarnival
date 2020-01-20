package ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.LevelManager;

/** A class representing all possible interaction that can occurs with a Sprite */
public class Sprite extends SpriteBinder {
  private boolean userInputIsDisabled;

  Sprite(View view, SpriteResource resource) {
    super(view, resource);
    userInputIsDisabled = false;
  }

  /**
   * Moves the sprite left when the game is active
   *
   * @param animationEngine The animation engine used in this game
   */
  public void moveLeft(@NonNull AnimationEngine animationEngine) {
    if (userInputIsDisabled) return;
    boolean hasMoved = positionManager.positionSprite("LEFT", vineComponents);
    if (hasMoved)
      animationManager.onMoveToSideAnimation(animationEngine, vineComponents, onTopOfVine);
  }

  /**
   * Moves the sprite right when the game is active
   *
   * @param animationEngine The animation engine used in this game
   */
  public void moveRight(@NonNull AnimationEngine animationEngine) {
    if (userInputIsDisabled) return;
    boolean hasMoved = positionManager.positionSprite("RIGHT", vineComponents);
    if (hasMoved)
      animationManager.onMoveToSideAnimation(animationEngine, vineComponents, onTopOfVine);
  }

  /**
   * Begins the Sprite animation that makes the sprite like like it is descending the vine
   *
   * @param animationEngine  The animation engine used in this game
   */
  public void showOnVineSprite(@NonNull AnimationEngine animationEngine) {
    animationManager.onVineAnimation(animationEngine, legs, hands, blink);
  }

  /** @return The position of the sprite which can either be left or right */
  public String getPosition() {
    return positionManager.getPosition();
  }

  /**
   * Delegates the animation the the animation manager that shows the end level sequence
   *
   * @param animationEngine The animation engine used in this game
   * @param levelManager The LevelManager used in this game
   */
  public void startEndLevelAnimation(AnimationEngine animationEngine, LevelManager levelManager) {
    userInputIsDisabled = true;
    animationManager.endLevelAnimation(
        animationEngine,
        vineComponents,
        walkComponents,
        peace,
        onTopOfVine,
        levelManager.getLevelStatusMutableObservable());
  }

  /**
   * Delegates the animation for when the sprite interact with an object that hurts it
   * @param animationEngine The animation engine used in this game */
  public void hurt(AnimationEngine animationEngine) {
    animationManager.hurtAnimation(animationEngine, vineComponents, hurt, onTopOfVine);
  }

  /**
   * Makes the sprite visible and user interactable
   * */
  public void setSpriteVisibilityAndInput() {
    userInputIsDisabled = false;
    for (ImageView vineComponent : vineComponents) {
      vineComponent.setVisibility(View.VISIBLE);
    }
  }
}
