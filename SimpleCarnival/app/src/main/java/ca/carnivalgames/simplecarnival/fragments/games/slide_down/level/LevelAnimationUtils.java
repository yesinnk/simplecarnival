package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.LevelSegment;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItemMap;

import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.getLeftIDs;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.getLevelItemViews;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.getRightIDs;

public class LevelAnimationUtils {
  /**
   * Gets all LevelItems currently display on the screen sorted by left and right
   *
   * @param layoutManager The linear layout manager of the level recycler view
   * @param segmentIndex A list of indices in the level recyclerView that are visible
   * @return A left and right list of LevelItems currently displayed on the screen. Returns null if
   *     the view holder does not exist
   */
  @Nullable
  static List<List<ImageView>> getLevelItemsInViewHolder(
      int segmentIndex, LinearLayoutManager layoutManager) {
    List<List<ImageView>> itemsDisplayedOnScreen = new ArrayList<>();

    // Add Left and Right List
    itemsDisplayedOnScreen.add(new ArrayList<ImageView>());
    itemsDisplayedOnScreen.add(new ArrayList<ImageView>());

    // For All Visible ViewHolder, get items and add to list itemsDisplayedOnScreen

    View viewHolder = layoutManager.findViewByPosition(segmentIndex);
    if (viewHolder != null) {
      itemsDisplayedOnScreen.get(0).addAll(getLevelItemViews(getLeftIDs(), viewHolder));
      itemsDisplayedOnScreen.get(1).addAll(getLevelItemViews(getRightIDs(), viewHolder));
    } else return null;

    return itemsDisplayedOnScreen;
  }

  /**
   * Gets a List of Indices in the RecyclerView that is currently displayed on screen
   *
   * @param yPos The current Y position in the level recycler view
   * @return A list of indices in the level recyclerView that are visible
   */
  static List<Integer> getVisibleSegmentIndices(int yPos) {
    ArrayList<Integer> visibleIndices = new ArrayList<>();

    // Get Second Y coordinate
    float visibilityBufferHeight = SlideUtils.getLevelSegmentHeight();
    float y2pos = yPos + visibilityBufferHeight - 1;

    // Get Current Visible Indices of ViewHolders and LevelData
    visibleIndices.add((int) Math.floor(yPos / visibilityBufferHeight));
    visibleIndices.add((int) Math.floor(y2pos / visibilityBufferHeight));

    return visibleIndices;
  }

  /**
   * A helper method that helps animate Level Items in recyclerView based on the range of rows
   * provided
   *
   * @param segment The Level Segment data used to populate the recyclerView
   * @param segmentImageViews The image views associated to each level segment
   * @param firstItemRow The first visible row of level items on screen that needs animation
   * @param lastItemRow The last visible row of level items on screen that needs animation
   */
  static void animateItemsOnScreen(
      LevelSegment segment,
      List<List<ImageView>> segmentImageViews,
      int firstItemRow,
      int lastItemRow) {
    if (segment == null || segment.getLevelItemMap() == null) return;

    List<ImageView> itemsLeft = segmentImageViews.get(0);
    List<ImageView> itemsRight = segmentImageViews.get(1);

    LevelItemMap levelItemMap = segment.getLevelItemMap();
    LevelItem[] leftLevelItemsData = levelItemMap.getSideLeft();
    LevelItem[] rightLevelItemsData = levelItemMap.getSideRight();

    for (int row = firstItemRow; row <= lastItemRow; row++) {
      if (leftLevelItemsData[row] != null)
        itemsLeft.get(row).setImageResource(leftLevelItemsData[row].getNextResourceID());
      else itemsLeft.get(row).setImageBitmap(null);
      if (rightLevelItemsData[row] != null)
        itemsRight.get(row).setImageResource(rightLevelItemsData[row].getNextResourceID());
      else itemsRight.get(row).setImageBitmap(null);
    }
  }



}
