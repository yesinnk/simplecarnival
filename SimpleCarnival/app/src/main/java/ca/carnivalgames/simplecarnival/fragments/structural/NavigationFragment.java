package ca.carnivalgames.simplecarnival.fragments.structural;

import android.content.Context;

import androidx.fragment.app.Fragment;

import ca.carnivalgames.simplecarnival.ViewController;

public abstract class NavigationFragment extends Fragment {
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    ViewController controller = (ViewController) getActivity();
    if (controller != null) {
      controller.showNavControls();
    }
  }
}
