package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types;

/** Every Item in the recycler view can be animated */
public abstract class LevelItemAnimator {
  private int nextResource;
  private boolean finalAnimation = false;

  public abstract int[] getResourceIDs();

  /**
   * The AnimationEngine will call this function when the item is currently displayed on the screen
   * and updates the views accordingly
   *
   * <p>If the finalAnimation flag is checked the item will not loop its animation
   *
   * @return The resource id of the next frame in animation
   */
  public int getNextResourceID() {
    if (!finalAnimation)
      nextResource = nextResource + 1 < getResourceIDs().length ? nextResource + 1 : 0;
    else
      nextResource = nextResource + 1 < getResourceIDs().length ? nextResource + 1 : nextResource;
    return getResourceIDs()[nextResource];
  }

  /**
   * Sets the finalAnimation flag to true. When the animator gets new frames it will stop at the
   * last frame
   */
  void setFinalAnimation() {
    nextResource = -1;
    this.finalAnimation = true;
  }
}
