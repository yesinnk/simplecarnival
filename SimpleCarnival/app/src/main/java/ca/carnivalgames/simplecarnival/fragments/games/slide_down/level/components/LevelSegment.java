package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components;

import android.graphics.Bitmap;

import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItemMap;

/**
 * Level segment data is used to populate the recycler view with information for the background and
 * LevelItem resources
 */
public class LevelSegment {

  private final int vine;
  private final Bitmap background;
  private final Bitmap lastBackground;
  private final LevelItemMap levelItemMap;

  public LevelSegment(
      int vine, Bitmap background, Bitmap lastBackground, LevelItemMap levelItemMap) {
    this.vine = vine;
    this.background = background;
    this.lastBackground = lastBackground;
    this.levelItemMap = levelItemMap;
  }

  /** @return gets the vine drawable resource in the RecyclerView */
  int getVineResourceID() {
    return vine;
  }

  /** @return gets the background drawable resource to be used in the RecyclerView */
  Bitmap getBackgroundBitmap() {
    return background;
  }

  /**
   * @return gets the merging background drawable resource to be used in the RecyclerView. This
   *     makes background changes appear to be seamless
   */
  Bitmap getLastBackgroundBitmap() {
    return lastBackground;
  }

  /** @return get the map of LevelItems that will be displayed in this segment */
  public LevelItemMap getLevelItemMap() {
    return levelItemMap;
  }
}
