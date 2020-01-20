package ca.carnivalgames.simplecarnival.fragments.games.slide_down;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.Utils;

/**
 * A utility class that helps with different set of problems in the game
 * */
public class SlideUtils {
  private static final int LEVEL_SEGMENT_HEIGHT = 800;
  private static final int NOT_DEFINED_BACKGROUND = 0;
  private static final int LEVEL_ITEM_ROW_COUNT = 4;
  private static Bitmap clipMap;

  /** @return  The height of the level segment in the recyclerView as density pixel */
  public static float getLevelSegmentHeight() {
    return Utils.Measurement.pixelToDP(LEVEL_SEGMENT_HEIGHT);
  }

  /** @return  The height of the level segment row in the recyclerView as density pixel */
  public static float getLevelSegmentItemRowHeight() {
    return Utils.Measurement.pixelToDP(LEVEL_SEGMENT_HEIGHT) / 4;
  }

  /**
   * Generates a new bitmap with a triangle mask cutout
   * @param resources The android resource needed to get drawables
   * @param backgroundResourceId The id of the background resources
   * @return A masked bitmap with triangle cutouts
   */
  public static Bitmap generateClippedImage(Resources resources, int backgroundResourceId) {
    if (backgroundResourceId == NOT_DEFINED_BACKGROUND) return null;
    Bitmap ogBitmap = BitmapFactory.decodeResource(resources, backgroundResourceId);
    return ca.carnivalgames.simplecarnival.Utils.ImageTools.createClippedImage(
        ogBitmap, getClipMap(resources));
  }

  /**
   * Generates a new bitmap with a triangle mask cutout showing the top portion of triangles
   * @param resources The android resource needed to get drawables
   * @param backgroundResourceId The id of the background resources
   * @return A masked bitmap with triangle cutouts showing the top portion
   */
  public static Bitmap generateInversedClippedImage(Resources resources, int backgroundResourceId) {
    if (backgroundResourceId == NOT_DEFINED_BACKGROUND) return null;
    Bitmap ogBitmap = BitmapFactory.decodeResource(resources, backgroundResourceId);
    return ca.carnivalgames.simplecarnival.Utils.ImageTools.createInversedClippedImage(
        ogBitmap, getClipMap(resources));
  }

  /**
   * Generates a new bitmap from drawable resource
   * @param resources The android resource needed to get drawables
   * @param backgroundResourceId The id of the background resources
   * @return A new bitmap from drawable resource
   */
  public static Bitmap generateImage(Resources resources, int resourceId) {
    return resourceId == NOT_DEFINED_BACKGROUND ? null : BitmapFactory.decodeResource(resources, resourceId);
  }

  /**
   * Generates a new bitmap used for masking
   * @param resources The android resource needed to get drawables
   * @return A bitmap representing the mask of other bitmaps
   */
  static Bitmap getClipMap(Resources resources) {
    if (clipMap == null) {
      clipMap = BitmapFactory.decodeResource(resources, R.drawable.slide_clip_map_500);
    }
    return clipMap;
  }

  /** @return Generates the left ids used for the gird of LevelItems in the LevelRecycler */
  public static int[] getLeftIDs() {
    return new int[] {R.id.slide_l_1, R.id.slide_l_2, R.id.slide_l_3, R.id.slide_l_4};
  }

  /** @return Generates the left ids used for the gird of LevelItems in the LevelRecycler */
  public static int[] getRightIDs() {
    return new int[] {R.id.slide_r_1, R.id.slide_r_2, R.id.slide_r_3, R.id.slide_r_4};
  }

  /** @return gets item row count used in the grid of LevelItems*/
  public static int getLevelItemRowCount() {
    return LEVEL_ITEM_ROW_COUNT;
  }

  /**
   * A helper function that returns a list of images views in a ViewHolder given by a list of Ids
   *
   * @param ids The ids of ImageViews inside the View Holder that need to bind
   * @param itemViewHolder The Holder View of all the images views
   * @return A list of ImagesViews (binded XML-Java) from the ViewHolder
   */
  public static ArrayList<ImageView> getLevelItemViews(int[] ids, View itemViewHolder) {
    ArrayList<ImageView> views = new ArrayList<>();
    for (int id : ids) {
      views.add((ImageView) itemViewHolder.findViewById(id));
    }
    return views;
  }
}
