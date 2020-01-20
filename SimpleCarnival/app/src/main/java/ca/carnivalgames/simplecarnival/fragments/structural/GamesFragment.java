package ca.carnivalgames.simplecarnival.fragments.structural;

import android.content.Context;
import android.util.Log;

import ca.carnivalgames.simplecarnival.ViewController;
import ca.carnivalgames.simplecarnival.fragments.games.GameInfo;
import ca.carnivalgames.simplecarnival.persistence.GameStatistic;
import ca.carnivalgames.simplecarnival.persistence.UserState;

/**
 * Abstract class for game to instantiate saving, and pausing the game
 */

public abstract class GamesFragment extends RepositoryFragment {

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    ViewController controller = (ViewController) getActivity();
    if (controller != null) {
      // Temporary do not merge with NavigationFragment
      controller.showNavControls();
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    saveGameInfo(onSaveGame());
    onGamePause();
  }

  private void saveGameInfo(GameInfo gameInfo) {

    if (UserState.getLoggedIn() != null && gameInfo != null) {
      String userGamerId = UserState.getCurrentUserID() + gameInfo.getGameTitle();
      String userName = UserState.getLoggedIn().username;
      String gameName = gameInfo.getGameTitle();
      appRepository.saveGame(
          new GameStatistic(
              userGamerId,
              userName,
              gameName,
              gameInfo.getUserScore(),
              gameInfo.getUserLevel(),
              gameInfo.getCoinsCollect(),
              System.currentTimeMillis()));

    }
  }

  protected GameInfo onSaveGame() {
    return null;
  }

  protected abstract void onGamePause();
}
