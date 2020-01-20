package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components;

import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.LevelItemType;

/**
 * LevelItemPainter helps paint items on to each screen using a system a priority level. If the item
 * should be painted on the screen in every possible block then the level should be set to 1, Set to
 * 2 for every other etc.
 */
class LevelItemPainter {
  private final LevelItemType objectType;
  private final int priority;
  private final int offset;

  LevelItemPainter(LevelItemType objectType, int priority) {
    this.objectType = objectType;
    this.priority = priority;
    this.offset = (int) (priority * Math.random());
  }

  LevelItemType getObjectType() {
    return objectType;
  }

  /**
   * Calculates whether an item should exist on the screen by a given index
   *
   * @param index The index represent the row of which an item can exist in an item grid
   * @return Whether or not an item should be painted into the grid
   */
  boolean shouldPaintRow(int index) {
    return ((index + offset) % priority) == 0;
  }

  /**
   * Items can only exist on side per row this helper helps generate the side of the two columns a
   * item should exists
   *
   * @return A randomly generated side of either left or right
   */
  String getRandomSide() {
    return Math.random() < 0.5 ? "left" : "right";
  }
}
