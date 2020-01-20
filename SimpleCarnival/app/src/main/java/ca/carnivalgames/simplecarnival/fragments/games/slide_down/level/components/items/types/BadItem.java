package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types;

import android.widget.ImageView;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.SlideLevelManager;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite.Sprite;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.status.ScoreKeeper;

/**
 * A Bad item represents a item that when a sprite interacts with it has a negative effect on the
 * sprite and slows down the gameplay
 */
public abstract class BadItem extends LevelItemAnimator implements LevelItem {

  /** See {@link LevelItem#interact} */
  @Override
  public void interact(
      Sprite sprite,
      ScoreKeeper score,
      SlideLevelManager levelManager,
      ImageView itemImageView,
      AnimationEngine animationEngine) {
    levelManager.resetSpeed();
    sprite.hurt(animationEngine);
  }
}
