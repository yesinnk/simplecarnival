package ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite;

import android.view.View;

/**
 * A factory class used for creating sprites. Even though their is only one sprite it allows the
 * expansion of many more easily
 */
public class SpriteFactory {
  public static Sprite create(String spriteName, View view) {
    SpriteResource spriteResource = null;
    if (spriteName.equalsIgnoreCase("mario")) {
      spriteResource = new Mario();
    }

    if (spriteResource != null) {
      return new Sprite(view, spriteResource);
    }
    return null;
  }
}
