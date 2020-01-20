package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.levels;

import android.content.res.Resources;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.SectionBuilder;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.LevelItemType;

/** see {@link SectionBuilder} */
public class LevelTwo extends SectionBuilder {
  public LevelTwo(Resources resources) {
    super(resources);

    setLevelWorstTime(100);
    addVine(R.drawable.slide_repeated_vines).repeatBackground(2).addToLevel();

    addVine(R.drawable.slide_repeated_vines)
        .addLevelItem(LevelItemType.Goomba, 1)
        .addBackground(R.drawable.slide_background_weird_eyes)
        .repeatBackground(3)
        .addToLevel();

    addVine(R.drawable.slide_repeated_vines).repeatBackground(2)
            .addLevelItem(LevelItemType.Coins, 1)
            .addToLevel();


    addVine(R.drawable.slide_repeated_vines)
        .addBackground(R.drawable.slide_background_purple_500)
            .addLevelItem(LevelItemType.Goomba, 1)
        .repeatBackground(4)
        .addToLevel();

    addVine(R.drawable.slide_repeated_vines)
            .addBackground(R.drawable.slide_background_cacti_500)
            .addLevelItem(LevelItemType.Coins, 1)
            .addLevelItem(LevelItemType.KoopaTroopa, 4)
            .repeatBackground(3)
            .addToLevel();

    addVine(R.drawable.slide_repeated_vines)
        .addLevelItem(LevelItemType.Goomba, 1)
        .addBackground(R.drawable.slide_background_catci_pot_500)
        .repeatBackground(0)
        .addToLevel();

    addVine(R.drawable.slide_repeated_vines).repeatBackground(2).addToLevel();
  }
}
