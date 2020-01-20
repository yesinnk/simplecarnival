package ca.carnivalgames.simplecarnival.fragments.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.Utils;

/** The recyclerView adapter to be used for the MenuFragment*/
class MenuAdapter extends ListAdapter<MenuItem, MenuAdapter.ViewHolder> {

  private final MenuItemClickDelegate menuItemClickDelegate;

  MenuAdapter(MenuItemClickDelegate menuFragment) {
    super(new Utils.UnusedDiffer<MenuItem>());
    menuItemClickDelegate = menuFragment;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    View layout = layoutInflater.inflate(R.layout.fragment_menu_recycler_item, parent, false);
    return new ViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    ViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    void bind(final MenuItem menuItem) {
      ImageView image = itemView.findViewById(R.id.menu_item_image);
      TextView title = itemView.findViewById(R.id.menu_recycler_item_text);
      title.setText(menuItem.getTitle());
      image.setImageResource(menuItem.getResourceId());
      image.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              menuItemClickDelegate.onMenuItemClick(menuItem, getAdapterPosition());
            }
          });
    }
  }
}
