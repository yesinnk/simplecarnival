package ca.carnivalgames.simplecarnival.fragments.menu;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.games.create_a_shape.CatchAShape;
import ca.carnivalgames.simplecarnival.fragments.games.cup_shuffler.CupShuffler;
import ca.carnivalgames.simplecarnival.fragments.games.petting_zoo.PettingZoo;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideDown;

/** A class that helps manage all the games that will be in the menu*/
public class Menu {

  private static final List<MenuItem> menuModelList = new ArrayList<>();

  static {
    addGame(CatchAShape.getTitle(), R.drawable.catch_a_shape, CatchAShape.class);
    addGame(CupShuffler.getTitle(), R.drawable.cup_shuffler, CupShuffler.class);
    addGame(PettingZoo.getTitle(), R.drawable.petting_zoo, PettingZoo.class);
    addGame(SlideDown.getTitle(), R.drawable.menu_vine_bros, SlideDown.class);
  }

  private static void addGame(@NonNull String title, int resourceId, @NonNull Class gamesFragment) {
    menuModelList.add(new MenuItem(title, resourceId, gamesFragment));
  }

  public static List<MenuItem> getItemList() {
    return menuModelList;
  }
}
