package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items;

/**
 * A map representing the left and right objects that will be displayed in the RecyclerView
 * ImageView grid of LevelItems
 */
public class LevelItemMap {
  private final LevelItem[] sideLeft;
  private final LevelItem[] sideRight;

  public LevelItemMap(int size) {
    this.sideLeft = new LevelItem[size];
    this.sideRight = new LevelItem[size];
  }

  /**
   * Method use to put items in the LevelItemMap
   *
   * @param side The column side of the grid the LevelItem belongs
   * @param row The row of the column that LevelItem belongs
   * @param levelItem The level item that will be used to be displayed in the LevelRecyclerView
   */
  public void put(String side, int row, LevelItem levelItem) {
    LevelItem[] levelItemArray = side == "left" ? sideLeft : sideRight;
    levelItemArray[row] = levelItem;
  }

  /** @return The list of items belongs to the left column in the LevelItemMap */
  public LevelItem[] getSideLeft() {
    return sideLeft;
  }

  /** @return The list of items belongs to the right column in the LevelItemMap */
  public LevelItem[] getSideRight() {
    return sideRight;
  }
}
