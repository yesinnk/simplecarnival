package ca.carnivalgames.simplecarnival.fragments.login;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.persistence.User;

class LoginAdapter extends ListAdapter<User, LoginAdapter.ViewHolder> {
  private int itemClickIndex = -1;

  private final LoginItemClickDelegate loginItemClickDelegate;

  LoginAdapter(LoginItemClickDelegate menuFragment) {
    super(DIFF_UTILITY);
    loginItemClickDelegate = menuFragment;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View layout = layoutInflater.inflate(R.layout.fragment_login_recycler_item, parent, false);
    return new ViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
    if (position == itemClickIndex) holder.setClicked();
    else holder.setNormal();
  }

  // Todo Implement Differ
  private static final DiffUtil.ItemCallback<User> DIFF_UTILITY =
      new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldUser, @NonNull User newUser) {
          return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldUser, @NonNull User newUser) {
          return false;
        }
      };

  class ViewHolder extends RecyclerView.ViewHolder {

    final MaterialCardView card;
    final int backgroundColor;
    final int textColor;
    final TextView username;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      card = itemView.findViewById(R.id.login_user_picker_recycler_user_card);
      username = itemView.findViewById(R.id.login_user_picker_recycler_text);

      backgroundColor = card.getCardBackgroundColor().getDefaultColor();
      textColor = username.getTextColors().getDefaultColor();
    }

    void setNormal() {
      card.setCardBackgroundColor(backgroundColor);
      username.setTextColor(textColor);
    }

    void setClicked() {
      card.setCardBackgroundColor(Color.parseColor("#344377"));
      username.setTextColor(Color.WHITE);
    }

    void bind(final User user) {

      username.setText(user.username);

      card.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              itemClickIndex = getAdapterPosition();
              loginItemClickDelegate.onMenuItemClick(user, getAdapterPosition());
              notifyDataSetChanged();
            }
          });
    }
  }
}
