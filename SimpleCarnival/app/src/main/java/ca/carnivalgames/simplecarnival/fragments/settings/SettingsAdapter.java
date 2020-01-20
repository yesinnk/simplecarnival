package ca.carnivalgames.simplecarnival.fragments.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ca.carnivalgames.simplecarnival.R;

class SettingsAdapter extends ListAdapter<ScoreboardStatistic, SettingsAdapter.ViewHolder> {

  SettingsAdapter() {
    super(DIFF_UTILITY);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View layout = layoutInflater.inflate(R.layout.fragment_settings_stats_item, parent, false);
    return new ViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  // Todo Implement Differ
  private static final DiffUtil.ItemCallback<ScoreboardStatistic> DIFF_UTILITY =
      new DiffUtil.ItemCallback<ScoreboardStatistic>() {
        @Override
        public boolean areItemsTheSame(
            @NonNull ScoreboardStatistic oldUser, @NonNull ScoreboardStatistic newUser) {
          return false;
        }

        @Override
        public boolean areContentsTheSame(
            @NonNull ScoreboardStatistic oldUser, @NonNull ScoreboardStatistic newUser) {
          return false;
        }
      };


  class ViewHolder extends RecyclerView.ViewHolder {

    ViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    void bind(final ScoreboardStatistic scoreboardStatistic) {
        TextView title = itemView.findViewById(R.id.settings_game_title_text);
        TextView topScore = itemView.findViewById(R.id.settings_top_score_text);
        TextView topUser = itemView.findViewById(R.id.settings_top_user_name);
        TextView topDate = itemView.findViewById(R.id.settings_date_text);
        TextView userScore = itemView.findViewById(R.id.settings_stat_score_text);
        TextView userLevel = itemView.findViewById(R.id.settings_stat_level_text);
        TextView userCoins = itemView.findViewById(R.id.settings_stat_coins_text);

        title.setText(scoreboardStatistic.getGameTitle());
        topScore.setText(String.valueOf(scoreboardStatistic.getTopScore()));
        topUser.setText(scoreboardStatistic.getTopUserName());
        topDate.setText(scoreboardStatistic.getTopUserScoreDate());
        userScore.setText(String.valueOf(scoreboardStatistic.getUserScore()));
        userLevel.setText(String.valueOf(scoreboardStatistic.getUserLevel()));
        userCoins.setText(String.valueOf(scoreboardStatistic.getUserCoin()));
    }
  }
}
