package ca.carnivalgames.simplecarnival.fragments.structural;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import ca.carnivalgames.simplecarnival.viewmodels.AppRepository;

public abstract class RepositoryFragment extends SimpleFragment {
  protected AppRepository appRepository;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (getActivity() != null) {
      appRepository = ViewModelProviders.of(getActivity()).get(AppRepository.class);
    }
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
