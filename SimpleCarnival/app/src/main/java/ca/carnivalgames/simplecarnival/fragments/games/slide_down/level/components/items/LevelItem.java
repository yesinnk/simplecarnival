package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items;

import android.widget.ImageView;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.SlideLevelManager;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite.Sprite;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.status.ScoreKeeper;

/**
 * The interface representing level items that will be generated in each segment of the recyclerView
 */
public interface LevelItem {
  /**
   * This method is called when a level item has been interacted with. If the level affects the
   * sprite it can call methods directly on the sprite. Each LevelItem can have the possibility of
   * affecting the sprite, score, level elements such as speed
   *
   * @param sprite The sprite of which is interacting with this LevelItem
   * @param score The scoreKeeper object for this game
   * @param levelManager The current level Manager for this game
   * @param itemImageView The current ImageView that represents this item
   * @param animationEngine The animation engine used in this game
   */
  void interact(
          Sprite sprite,
          ScoreKeeper score,
          SlideLevelManager levelManager,
          ImageView itemImageView,
          AnimationEngine animationEngine);

  int[] getResourceIDs();

  int getSize();

  int getNextResourceID();
}
