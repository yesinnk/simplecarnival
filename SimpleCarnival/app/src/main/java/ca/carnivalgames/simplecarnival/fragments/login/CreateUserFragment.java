package ca.carnivalgames.simplecarnival.fragments.login;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.menu.Menu;
import ca.carnivalgames.simplecarnival.fragments.menu.MenuItem;
import ca.carnivalgames.simplecarnival.fragments.structural.RepositoryFragment;
import ca.carnivalgames.simplecarnival.persistence.UserState;
import ca.carnivalgames.simplecarnival.viewmodels.LoginViewModel;

public class CreateUserFragment extends RepositoryFragment {
  private TextInputLayout textInputLayout;
  private TextInputEditText textInputEditText;
  private MaterialButton login;
  private ImageView background;
  private int maxHeight;
  private View backButton;

  @Override
  protected int getLayout() {
    return R.layout.fragment_login_onboard;
  }

  @Override
  protected void setUpView(View view) {
    setExitTransition(new Fade());
    bind(view);
    if (UserState.hasUsers()) {
      backButton.setVisibility(View.VISIBLE);
    }

    // Login Button
    login.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            handleUserInput(textInputLayout);
          }
        });
    // Back Button
    backButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            LoginViewModel loginViewModel =
                ViewModelProviders.of(getParentFragment()).get(LoginViewModel.class);
            loginViewModel.show(LoginViewModel.USER_PICKER);
          }
        });
    // View Resize
    if (background != null)
      view.addOnLayoutChangeListener(
          new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(
                View view, int l, int t, int r, int bottom, int oL, int oT, int oR, int oBottom) {
              handleLayoutResize(oBottom, bottom);
            }
          });

  }

  private void bind(View view) {
    textInputLayout = view.findViewById(R.id.login_on_board_text_input);
    textInputEditText = view.findViewById(R.id.login_on_board_edit_text);
    login = view.findViewById(R.id.login_on_board_button);
    background = view.findViewById(R.id.login_on_board_bg);
    backButton = view.findViewById(R.id.login_back_button);
  }

  private void handleUserInput(final TextInputLayout textInputLayout) {
    if (textInputEditText.getText() == null) return;
    String userInput = textInputEditText.getText().toString();
    if (userInput.length() == 0 || userInput.matches("\\s*")) {
      textInputLayout.setError("Please enter a username");
    } else {
      LiveData<Boolean> userWasSavedObservable = appRepository.setUpNewUser(userInput);
      userWasSavedObservable.observe(
          this,
          new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userWasSaved) {
              if (!userWasSaved) textInputLayout.setError("Username has already been taken");
            }
          });
    }
  }


  private void handleLayoutResize(int oldBottom, int newBottom) {
    maxHeight = maxHeight > newBottom ? maxHeight : newBottom;
    if (newBottom < maxHeight) background.setVisibility(View.INVISIBLE);
    else background.setVisibility(View.VISIBLE);
  }


}
