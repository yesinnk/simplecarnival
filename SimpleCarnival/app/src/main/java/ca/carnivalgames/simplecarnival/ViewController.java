package ca.carnivalgames.simplecarnival;

import androidx.fragment.app.Fragment;

// Todo: Documentation
public interface ViewController {
  void hideGameControls();

  void showGameControls();

  void hideNavControls();

  void showNavControls();

  void loadFragment(Fragment destination);
}
