package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import android.widget.ImageView;

import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;

/**
 * Represents the Level Items and its associated ImageViews that has interacted with a sprite
 * */
public class InteractionEvent {
  private final LevelItem levelItemLeft;
  private final LevelItem levelItemRight;
  private final ImageView imageViewLeft;
  private final ImageView imageViewRight;
  private final int eventID;

  InteractionEvent(
          LevelItem levelItemLeft,
          LevelItem levelItemRight,
          ImageView imageViewLeft,
          ImageView imageViewRight, int eventID) {
    this.levelItemLeft = levelItemLeft;
    this.levelItemRight = levelItemRight;
    this.imageViewLeft = imageViewLeft;
    this.imageViewRight = imageViewRight;
    this.eventID = eventID;
  }

  public LevelItem getLevelItemLeft() {
    return levelItemLeft;
  }

  public LevelItem getLevelItemRight() {
    return levelItemRight;
  }

  public ImageView getImageViewLeft() {
    return imageViewLeft;
  }

  public ImageView getImageViewRight() {
    return imageViewRight;
  }
}
