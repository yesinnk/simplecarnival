package ca.carnivalgames.simplecarnival.fragments.structural;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// Todo: Documentation
public abstract class SimpleFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(getLayout(), container, false);
    setUpView(view);
    return view;
  }

  protected abstract int getLayout();

  protected abstract void setUpView(View view);
}
