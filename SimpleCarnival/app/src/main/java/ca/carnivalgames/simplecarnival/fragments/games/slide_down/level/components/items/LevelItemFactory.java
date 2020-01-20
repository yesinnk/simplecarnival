package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items;

import androidx.annotation.Nullable;

import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.BigCoinItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.BooItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.CoinItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.GoombaItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.KoopaTroopaItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.LevelItemType;

/** A factory class that helps with the generation of Level items used in the RecyclerView */
public class LevelItemFactory {
  @Nullable
  public static LevelItem createLevelItem(LevelItemType levelItemType) {
    if (levelItemType == LevelItemType.BigCoin) return new BigCoinItem();
    else if (levelItemType == LevelItemType.Boo) return new BooItem();
    else if (levelItemType == LevelItemType.Coins) return new CoinItem();
    else if (levelItemType == LevelItemType.Goomba) return new GoombaItem();
    else if (levelItemType == LevelItemType.KoopaTroopa) return new KoopaTroopaItem();
    return null;
  }
}
