package ca.carnivalgames.simplecarnival.fragments.menu;

import androidx.lifecycle.ViewModel;

public class MenuViewModel extends ViewModel {
  private int recyclerPosition;

  public int getRecyclerPosition() {
    return recyclerPosition;
  }

  public void setRecyclerPosition(int recyclerPosition) {
    this.recyclerPosition = recyclerPosition;
  }
}
