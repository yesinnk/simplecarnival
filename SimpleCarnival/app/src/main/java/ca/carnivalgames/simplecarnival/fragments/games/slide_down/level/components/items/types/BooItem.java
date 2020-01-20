package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;

public class BooItem extends BadItem implements LevelItem {

  /** The drawable resources used when this item appears */
  @Override
  public int[] getResourceIDs() {
    return new int[] {
      R.drawable.slide_coin_00000,
      R.drawable.slide_coin_00001,
      R.drawable.slide_coin_00002,
      R.drawable.slide_coin_00003
    };
  }

  /** See {@link LevelItem#getSize()} */
  @Override
  public int getSize() {
    return 0;
  }
}
