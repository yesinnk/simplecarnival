package ca.carnivalgames.simplecarnival.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
  @Insert
  long addUser(User user);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void setStatistic(GameStatistic gameStatistic);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void setInfo(AppInfo info);

  @Query("SELECT * FROM User")
  List<User> getAllUsersViaCurrentThread();

  @Query("SELECT * FROM User WHERE id = :userID LIMIT 1")
  List<User> getUserViaCurrentThread(int userID);

  @Query("SELECT * FROM User WHERE username = :username LIMIT 1")
  List<User> getUserViaCurrentThread(String username);

  @Query("SELECT * FROM AppInfo LIMIT 1")
  LiveData<AppInfo> getInfoLiveData();

  @Query("SELECT * FROM GameStatistic")
  List<GameStatistic> getAllUserStatistics();

  @Query("SELECT * FROM GameStatistic WHERE userName = :userName")
  List<GameStatistic> getAllUserStatisticsViaCurrentThread(String userName);

  @Query("SELECT * FROM GameStatistic WHERE gameTitle = :title")
  List<GameStatistic> getStatisticsForGame(String title);

  @Query("SELECT * FROM GameStatistic WHERE gameTitle = :title ORDER BY userScore DESC limit 1")
  List<GameStatistic> getTopStatisticsForGame(String title);
}
