package ca.carnivalgames.simplecarnival.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Database Singleton
@Database(
    entities = {User.class, AppInfo.class, GameStatistic.class},
    version = 1,
    exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

  private static AppDatabase INSTANCE;

  public static AppDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE =
          Room.databaseBuilder(
                  context.getApplicationContext(), AppDatabase.class, "Simple Carnival")
              // .allowMainThreadQueries()// only for testing purposes
              .build();
    }

    return INSTANCE;
  }

  public abstract AppDao dao();
}
