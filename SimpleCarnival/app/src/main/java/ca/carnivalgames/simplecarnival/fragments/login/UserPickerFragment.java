package ca.carnivalgames.simplecarnival.fragments.login;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.structural.RepositoryFragment;
import ca.carnivalgames.simplecarnival.persistence.AppInfo;
import ca.carnivalgames.simplecarnival.persistence.User;
import ca.carnivalgames.simplecarnival.persistence.UserState;
import ca.carnivalgames.simplecarnival.viewmodels.LoginViewModel;

public class UserPickerFragment extends RepositoryFragment implements LoginItemClickDelegate {

  private LoginViewModel loginViewModel;
  private Button signBackIn;
  private int userId = AppInfo.NOT_LOGGED_IN;

  @Override
  protected int getLayout() {
    return R.layout.fragment_login_user_picker;
  }

  @Override
  protected void setUpView(View view) {
    setExitTransition(new Fade());
    signBackIn = view.findViewById(R.id.login_user_picker_button);

    signBackIn.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (userId != AppInfo.NOT_LOGGED_IN) appRepository.signInUser(userId);
          }
        });

    Button createNewUserButton = view.findViewById(R.id.login_user_picker_create);
    createNewUserButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            if (getParentFragment() != null) {
              loginViewModel = ViewModelProviders.of(getParentFragment()).get(LoginViewModel.class);
              loginViewModel.show(LoginViewModel.CREATE_USER);
            }
          }
        });
    setUpRecycleView(view);
  }

  private void setUpRecycleView(View view) {
    LoginAdapter menuAdapter = new LoginAdapter(this);
    RecyclerView recyclerView = view.findViewById(R.id.login_user_picker_recycler);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

    recyclerView.setAdapter(menuAdapter);
    recyclerView.setLayoutManager(layoutManager);
    menuAdapter.submitList(UserState.getUserList());
  }

  @Override
  public void onMenuItemClick(User user, int index) {
    String buttonText = "Sign in as " + user.username;
    signBackIn.setText(buttonText);
    userId = user.id;
  }
}
