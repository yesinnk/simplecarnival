package ca.carnivalgames.simplecarnival.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.List;

import ca.carnivalgames.simplecarnival.Utils;
import ca.carnivalgames.simplecarnival.fragments.login.LoginFragment;
import ca.carnivalgames.simplecarnival.fragments.menu.MenuFragment;
import ca.carnivalgames.simplecarnival.persistence.AppInfo;
import ca.carnivalgames.simplecarnival.persistence.User;
import ca.carnivalgames.simplecarnival.persistence.UserState;

import static ca.carnivalgames.simplecarnival.persistence.AppInfo.NOT_LOGGED_IN;

public class ControllerViewModel extends DatabaseRepository {
  private final MutableLiveData<Fragment> currentFragmentObservable = new MutableLiveData<>();

  public ControllerViewModel(@NonNull Application application) {
    super(application);
    db().getInfoLiveData()
        .observeForever(
            new Observer<AppInfo>() {
              @Override
              public void onChanged(AppInfo info) {
                handleAppInfoChange(info);
              }
            });
  }

  private void handleAppInfoChange(final AppInfo info) {
    Utils.Threads.runOnDBThread(
        new Runnable() {
          @Override
          public void run() {
            boolean loggedOut = info == null || info.currentUserID == NOT_LOGGED_IN;
            if (loggedOut) {
              UserState.setInfo(db().getAllUsersViaCurrentThread(), NOT_LOGGED_IN);
              setControllerFragment(new LoginFragment());
            } else {
              List<User> user = db().getUserViaCurrentThread(info.currentUserID);
              UserState.setInfo(user, info.currentUserID);

              Log.e("Che: @ControllerViewModel", "Signed in as " + user.get(0).username);
              setControllerFragment(new MenuFragment());
            }
          }
        });
  }

  public LiveData<Fragment> getCurrentFragmentObservable() {
    return currentFragmentObservable;
  }

  public void setControllerFragment(Fragment fragment) {
    currentFragmentObservable.postValue(fragment);
  }
}
