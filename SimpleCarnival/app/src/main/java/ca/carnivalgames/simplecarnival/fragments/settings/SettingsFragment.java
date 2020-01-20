package ca.carnivalgames.simplecarnival.fragments.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.structural.NavigationFragment;
import ca.carnivalgames.simplecarnival.persistence.GameStatistic;
import ca.carnivalgames.simplecarnival.persistence.UserState;
import ca.carnivalgames.simplecarnival.viewmodels.AppRepository;

// Todo: Documentation
public class SettingsFragment extends NavigationFragment {
  private AppRepository appRepository;
    private SettingsAdapter settingsAdapter;

    @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_settings, container, false);
    bind(view);

    if(UserState.getLoggedIn() != null){
        TextView welcome = view.findViewById(R.id.settings_welcome);
        String welcomeText = "Welcome " + UserState.getLoggedIn().username;
        welcome.setText(welcomeText);
    }

    setUpRecycleView(view);
    setUpLiveData();
    return view;
  }

  private void bind(View view) {
    if (getActivity() != null) {
      final AppRepository appRepository =
          ViewModelProviders.of(getActivity()).get(AppRepository.class);
      view.findViewById(R.id.fragment_settings_logout)
          .setOnClickListener(
              new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  appRepository.setStatusToLoggedOut();
                }
              });
    }
  }

  private void setUpRecycleView(View view) {

      settingsAdapter = new SettingsAdapter();
    RecyclerView recyclerView = view.findViewById(R.id.settings_recycler);
    LinearLayoutManager layoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

    recyclerView.setAdapter(settingsAdapter);
    recyclerView.setLayoutManager(layoutManager);
  }

  private void setUpLiveData() {
    if (getActivity() != null) {
      appRepository = ViewModelProviders.of(getActivity()).get(AppRepository.class);
      LiveData<List<ScoreboardStatistic>> liveData = appRepository.getScoreBoardInfo();
      liveData.observe(this, new Observer<List<ScoreboardStatistic>>() {
          @Override
          public void onChanged(List<ScoreboardStatistic> scoreboardStatistics) {
              settingsAdapter.submitList(scoreboardStatistics);
          }
      });
    }
  }
}
