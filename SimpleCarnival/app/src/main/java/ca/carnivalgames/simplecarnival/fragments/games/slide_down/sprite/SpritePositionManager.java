package ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite;

import android.widget.ImageView;

import java.util.ArrayList;

import static ca.carnivalgames.simplecarnival.Utils.Measurement.pixelToDP;

/**
 * Used to keep track of the the position of a sprite and how to scale and translate into that
 * position
 */
class SpritePositionManager {
  private String position = "";

  /** Positions a sprite. If a sprite is already in that position this method return false else true
   * @param position The position that the sprite should move into
   * @param spriteComponents The components that make up the sprite on a vine
   * @return
   */
  boolean positionSprite(String position, ArrayList<ImageView> spriteComponents) {
    if (this.position.equalsIgnoreCase(position)) return false;

    float translateX = position.equals("LEFT") ? pixelToDP(-25) : pixelToDP(25);
    float scaleX = position.equals("LEFT") ? -2f : 2f;

    for (ImageView spriteComponent : spriteComponents) {
      spriteComponent.setTranslationX(translateX);
      spriteComponent.setScaleX(scaleX);
    }

    this.position = position;
    return true;
  }

  /** @return The current position the sprite is in*/
  public String getPosition() {
    return position.isEmpty() ? "LEFT" : position;
  }
}
