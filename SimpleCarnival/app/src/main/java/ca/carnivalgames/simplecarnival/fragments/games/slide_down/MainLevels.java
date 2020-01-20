package ca.carnivalgames.simplecarnival.fragments.games.slide_down;

import android.content.res.Resources;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.SectionBuilder;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.levels.LevelOne;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.levels.LevelTwo;

/** A list of levels that can be added to the level manager */
public class MainLevels extends ArrayList<SectionBuilder> {
  MainLevels(Resources resources) {
    add(new LevelOne(resources));
    add(new LevelTwo(resources));
  }
}
