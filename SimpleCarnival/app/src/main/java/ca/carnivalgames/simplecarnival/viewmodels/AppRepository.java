package ca.carnivalgames.simplecarnival.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ca.carnivalgames.simplecarnival.Utils;
import ca.carnivalgames.simplecarnival.fragments.settings.ScoreboardStatistic;
import ca.carnivalgames.simplecarnival.persistence.AppInfo;
import ca.carnivalgames.simplecarnival.persistence.GameStatistic;
import ca.carnivalgames.simplecarnival.persistence.User;
import ca.carnivalgames.simplecarnival.persistence.UserState;

import static ca.carnivalgames.simplecarnival.persistence.AppInfo.NOT_LOGGED_IN;

/**
 * A repository class that helps with the manipulation of data for the database in a threaded
 * environment
 */
public class AppRepository extends DatabaseRepository {
  private final MutableLiveData<Boolean> userWasSavedObservable = new MutableLiveData<>();
  private final MutableLiveData<List<ScoreboardStatistic>> scoreboardObservable =
      new MutableLiveData<>();

  ArrayList<String> gamesList = new ArrayList<>();

  {
    gamesList.add("Catch a Coin");
    gamesList.add("Petting Zoo");
    gamesList.add("Cup Shuffler");
    gamesList.add("Super Vine Bros");
  }

  public AppRepository(@NonNull Application application) {
    super(application);
  }

  /**
   * Sets a new app status when the user is logged out. This use the live data observer model which
   * will notify observers of changes such as the Controller Activity
   */
  public void setStatusToLoggedOut() {
    Utils.Threads.runOnDBThread(
        new Runnable() {
          @Override
          public void run() {
            db().setInfo(new AppInfo(System.currentTimeMillis(), NOT_LOGGED_IN));
          }
        });
  }

  /**
   * Adds a new user to the database
   *
   * @param username The user name to be added
   * @return
   */
  public LiveData<Boolean> setUpNewUser(final String username) {
    Utils.Threads.runOnDBThread(
        new Runnable() {
          @Override
          public void run() {
            boolean usernameExist = db().getUserViaCurrentThread(username).size() != 0;

            if (usernameExist) userWasSavedObservable.postValue(false);
            else {
              long id = db().addUser(new User(username, System.currentTimeMillis()));
              signInUser((int) id);
            }
          }
        });

    return userWasSavedObservable;
  }

  /** @param id the id of the user to sign in */
  public void signInUser(final int id) {
    Utils.Threads.runOnDBThread(
        new Runnable() {
          @Override
          public void run() {
            db().setInfo(new AppInfo(System.currentTimeMillis(), id));
          }
        });
  }

  /** @param statistic The statistic to save into the SQLite database */
  public void saveGame(final GameStatistic statistic) {
    Utils.Threads.runOnDBThread(
        new Runnable() {
          @Override
          public void run() {
            db().setStatistic(statistic);
          }
        });
  }

  /**
   * Uses the live data observer model to generate information in a background thread and when the
   * information is ready for consumption it sends data to the live data observers
   *
   * @return A live data that observes a list of scoreboard statistics
   */
  public LiveData<List<ScoreboardStatistic>> getScoreBoardInfo() {
    Utils.Threads.runOnDBThread(
        new Runnable() {
          @Override
          public void run() {
            if (UserState.getLoggedIn() != null) {
              //              List<MenuItem> menuItemList = Menu.getItemList();
              List<GameStatistic> scorePerGameForCurrentUser =
                  db().getAllUserStatisticsViaCurrentThread(UserState.getLoggedIn().username);

              List<ScoreboardStatistic> scoreboardStatistics = new ArrayList<>();
              for (String gameName : gamesList) {
                List<GameStatistic> topScoreList = db().getTopStatisticsForGame(gameName);
                if (topScoreList != null && topScoreList.size() > 0) {
                  GameStatistic topScore = topScoreList.get(0);
                  if (topScore != null) {
                    GameStatistic userGameStatistic =
                        getGameStatisticByGameNameInList(scorePerGameForCurrentUser, gameName);
                    scoreboardStatistics.add(new ScoreboardStatistic(topScore, userGameStatistic));
                  }
                }
              }
              scoreboardObservable.postValue(scoreboardStatistics);
            }
          }
        });

    return scoreboardObservable;
  }

  private GameStatistic getGameStatisticByGameNameInList(
      List<GameStatistic> listOfGames, String nameOfGame) {
    for (GameStatistic game : listOfGames) {
      if (game.getGameTitle().equals(nameOfGame)) {
        return game;
      }
    }

    return getEmptyGameStatistic(nameOfGame, UserState.getLoggedIn().username);
  }

  private GameStatistic getEmptyGameStatistic(String nameOfGame, String userName) {
    return new GameStatistic("null", userName, nameOfGame, 0, 0, 0, 0);
  }
}
