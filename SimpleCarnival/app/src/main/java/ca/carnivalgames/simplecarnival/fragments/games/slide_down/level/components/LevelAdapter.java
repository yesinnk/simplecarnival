package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.Utils;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItemMap;

import static ca.carnivalgames.simplecarnival.Utils.Measurement.pixelToDP;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.getLeftIDs;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.getLevelItemViews;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.getRightIDs;

/**
 * Recycler View Adapter used to display game level background repetitions and Level objects(Adapter
 * Pattern)
 */
public class LevelAdapter extends ListAdapter<LevelSegment, LevelAdapter.ViewHolder> {
  // Standard RecyclerView Adapter

  /** See {@link RecyclerView} for more information of the standard methods of the recyclerview */

  // The differ is not needed in this current implementation of the RecyclerView however it can be
  // be used in future versions of the game such as a game level that infinitely repeats
  public LevelAdapter() {
    super(new Utils.UnusedDiffer<LevelSegment>());
  }

  // This recycler view differ between the last view holder versus other view holders. This allow
  // level vine to be shorter in in the last view holder
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
    if (viewType == 0)
      return new ViewHolder(
          layoutInflater.inflate(R.layout.fragment_slide_down_level, parent, false));
    else
      return new ViewHolder(
          layoutInflater.inflate(R.layout.fragment_slide_down_level_end, parent, false));
  }

  // Helps determine that last view holder of the recyclerView
  @Override
  public int getItemViewType(int position) {
    return position != getItemCount() - 1 ? 0 : 1;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(getItem(position));
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private ArrayList<ImageView> itemsLeft;
    private ArrayList<ImageView> itemsRight;
    private LevelItemMap levelItemMap;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    /**
     * Helps bind level segments elements and represents them as view and the screen. Each segment
     * include LevelItems and backgrounds
     *
     * @param segment The segment data that includes background and LevelItems
     */
    void bind(LevelSegment segment) {

      //Binding
      ImageView vine = itemView.findViewById(R.id.slide_recycler_item_chain);
      ImageView background = itemView.findViewById(R.id.slide_recycler_item_bg);
      ImageView lastBackground = itemView.findViewById(R.id.slide_recycler_item_bg_last);

      // Set Vine and Background Resources
      vine.setBackgroundResource(segment.getVineResourceID());
      Bitmap backgroundBitmap = segment.getBackgroundBitmap();
      Bitmap lastBackgroundBitmap = segment.getLastBackgroundBitmap();

      setUpLevelItems(segment);
      background.setImageBitmap(backgroundBitmap);
      lastBackground.setImageBitmap(lastBackgroundBitmap);
    }

    /**
     * Populates LevelItem ImageView Holders with Sprite resources
     * @param segment */
    private void setUpLevelItems(LevelSegment segment) {
      itemsLeft = getLevelItemViews(getLeftIDs(), itemView);
      itemsRight = getLevelItemViews(getRightIDs(), itemView);

      levelItemMap = segment.getLevelItemMap();
      if (levelItemMap != null) {
        addItemImageResources(itemsLeft, levelItemMap.getSideLeft());
        addItemImageResources(itemsRight, levelItemMap.getSideRight());
      } else {
        addItemImageResources(itemsLeft, null);
        addItemImageResources(itemsRight, null);
      }
    }

    /**
     * Populates a list of ImageViews with resource drawable data.
     * @param itemImageViews List of views to that can be use to display LevelItems
     * @param levelItems The LevelItem data that will help populate itemImageViews
     */
    private void addItemImageResources(
        @NonNull ArrayList<ImageView> itemImageViews, @Nullable LevelItem[] levelItems) {
      for (int i = 0; i < itemImageViews.size(); i++) {
        if (levelItems != null && levelItems[i] != null) {
          ImageView imageView = itemImageViews.get(i);
          LevelItem levelItem = levelItems[i];
          ConstraintLayout.LayoutParams layoutParams =
              (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
          layoutParams.width = (int) pixelToDP(levelItem.getSize());
          layoutParams.height = (int) pixelToDP(levelItem.getSize());
          imageView.setLayoutParams(layoutParams);
          imageView.setImageResource(levelItem.getResourceIDs()[0]);

        } else itemImageViews.get(i).setImageBitmap(null);
      }
    }
  }
}
