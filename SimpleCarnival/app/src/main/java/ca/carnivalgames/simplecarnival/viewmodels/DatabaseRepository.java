package ca.carnivalgames.simplecarnival.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import ca.carnivalgames.simplecarnival.persistence.AppDao;
import ca.carnivalgames.simplecarnival.persistence.AppDatabase;

abstract class DatabaseRepository extends AndroidViewModel {
  DatabaseRepository(@NonNull Application application) {
    super(application);
  }

  AppDao db() {
    return AppDatabase.getInstance(getApplication()).dao();
  }
}
