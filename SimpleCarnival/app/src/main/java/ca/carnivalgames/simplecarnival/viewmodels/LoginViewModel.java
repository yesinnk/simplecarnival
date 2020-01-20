package ca.carnivalgames.simplecarnival.viewmodels;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ca.carnivalgames.simplecarnival.fragments.login.CreateUserFragment;
import ca.carnivalgames.simplecarnival.fragments.login.UserPickerFragment;
import ca.carnivalgames.simplecarnival.persistence.UserState;

public class LoginViewModel extends ViewModel {
  public static final int CREATE_USER = 0;
  public static final int USER_PICKER = 1;
  private final MutableLiveData<Fragment> loginFragmentObservable = new MutableLiveData<>();

  public void show(int view) {
    if (view == CREATE_USER) loginFragmentObservable.postValue(new CreateUserFragment());
    else if (view == USER_PICKER) loginFragmentObservable.postValue(new UserPickerFragment());
  }

  public void init() {
    if (UserState.isFirstLogIn()) loginFragmentObservable.postValue(new CreateUserFragment());
    else loginFragmentObservable.postValue(new UserPickerFragment());
  }

  public LiveData<Fragment> getLoginFragmentObservable() {
    return loginFragmentObservable;
  }
}
