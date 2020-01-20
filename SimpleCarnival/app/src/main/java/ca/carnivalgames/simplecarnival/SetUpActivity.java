package ca.carnivalgames.simplecarnival;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ca.carnivalgames.simplecarnival.viewmodels.ControllerViewModel;

/**
 * Helps Manage The bind for the main activity and set up listeners the back button to leave
 * activity fragments
 */
public abstract class SetUpActivity extends AppCompatActivity {
  View backButton;
  ControllerViewModel controllerViewModel;
  Boolean backToMenu = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    controllerViewModel = ViewModelProviders.of(this).get(ControllerViewModel.class);

    backButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            onShowHome();
          }
        });

    LiveData<Fragment> currentFragmentObservable =
        controllerViewModel.getCurrentFragmentObservable();
    currentFragmentObservable.observe(
        this,
        new Observer<Fragment>() {
          @Override
          public void onChanged(Fragment fragment) {
            Utils.Fragments.loadFragment(R.id.container, fragment, getSupportFragmentManager());
          }
        });
  }

  abstract void onShowHome();

  public void loadFragment(Fragment destination) {
    controllerViewModel.setControllerFragment(destination);
  }

  @Override
  public void onBackPressed() {
    if (backToMenu) onShowHome();
    else super.onBackPressed();
    backToMenu = false;
  }
}
