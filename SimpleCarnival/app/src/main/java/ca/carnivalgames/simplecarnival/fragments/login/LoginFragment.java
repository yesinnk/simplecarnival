package ca.carnivalgames.simplecarnival.fragments.login;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.Utils;
import ca.carnivalgames.simplecarnival.ViewController;
import ca.carnivalgames.simplecarnival.fragments.structural.SimpleFragment;
import ca.carnivalgames.simplecarnival.viewmodels.LoginViewModel;

public class LoginFragment extends SimpleFragment {

  @Override
  protected int getLayout() {
    return R.layout.fragment_login;
  }

  @Override
  protected void setUpView(View view) {
    if (getActivity() != null) {

      ((ViewController) getActivity()).hideNavControls();
      LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

      loginViewModel.init();
      LiveData<Fragment> fragmentObservable = loginViewModel.getLoginFragmentObservable();
      fragmentObservable.observe(
          this,
          new Observer<Fragment>() {
            @Override
            public void onChanged(Fragment fragment) {
              loadFragment(fragment);
            }
          });
    }
  }

  private void loadFragment(Fragment destination) {
    if (getActivity() != null)
      Utils.Fragments.loadFragment(R.id.login_container, destination, getChildFragmentManager());
  }
}
