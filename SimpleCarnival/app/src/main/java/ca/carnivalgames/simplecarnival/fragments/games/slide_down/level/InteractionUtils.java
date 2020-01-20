package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import android.os.Handler;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ca.carnivalgames.simplecarnival.Utils;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.LevelSegment;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;

import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.getLevelItemRowCount;

public class InteractionUtils {
  /**
   * Detects when the sprite can possibly interact with a LevelItem displayed on the screen on a
   * background thread. When detection occurs the live data object will post its value to the main
   * thread
   *
   * @param interactionEventMutableLiveData A live data object that will notify subscribes of an
   *     interaction event
   * @param yPos The current Y position in the level recycler view
   * @param segment The Level Segment data used to populate the recyclerView
   * @param segmentImageViews The ImageViews corresponding LevelItems that belong to the Level
   *     Segment
   * @param recyclerHeight The height of the recyclerView
   * @param segmentIndex The index representing the segment which visible on screen. For instance
   *     two segments may be displayed at once on the screen, 0 corresponds to the top and 1
   *     corresponds to the bottom segment
   * @param speedLimit The speed of which the recyclerView is scroll. This helps with accurately
   *     detecting segment level items interactions
   */
  static void detectInteraction(
      final MutableLiveData<InteractionEvent> interactionEventMutableLiveData,
      final int yPos,
      final LevelSegment segment,
      final List<List<ImageView>> segmentImageViews,
      final float recyclerHeight,
      final int segmentIndex,
      final int speedLimit) {

    Utils.Threads.runOnBackgroundThread(
        new Runnable() {
          @Override
          public void run() {

            float segmentHeight = SlideUtils.getLevelSegmentHeight();
            float offset = yPos % segmentHeight;
            float rowHeight = SlideUtils.getLevelSegmentItemRowHeight();
            float centerOfView = recyclerHeight / 2;
            float snapShotHeight = Utils.Measurement.pixelToDP(speedLimit);
            float marginValue = Utils.Measurement.pixelToDP(50);

            float y1 = centerOfView - snapShotHeight / 2 + offset;
            float y2 = centerOfView + snapShotHeight / 2 + offset;

            // If rows fall within the interaction zone then notify observers of interactionEvent.
            for (int i = 0; i < getLevelItemRowCount(); i++) {
              float itemPositionY = i * rowHeight + segmentIndex * segmentHeight + marginValue;
              if (itemPositionY > y1 && itemPositionY < y2 && segment.getLevelItemMap() != null) {
                // InteractionUtils
                final LevelItem levelItemLeft = segment.getLevelItemMap().getSideLeft()[i];
                final LevelItem levelItemRight = segment.getLevelItemMap().getSideRight()[i];
                final ImageView imageViewLeft = segmentImageViews.get(0).get(i);
                final ImageView imageViewRight = segmentImageViews.get(0).get(i);
                final int eventID = (int) (yPos / segmentHeight);

                // Post on Main Thread
                Handler mainThread = new Handler(imageViewLeft.getContext().getMainLooper());
                if (levelItemLeft != null || levelItemRight != null)
                  mainThread.post(
                      new Runnable() {
                        @Override
                        public void run() {
                          interactionEventMutableLiveData.setValue(
                              new InteractionEvent(
                                  levelItemLeft,
                                  levelItemRight,
                                  imageViewLeft,
                                  imageViewRight,
                                  eventID));
                        }
                      });
              }
            }
          }
        });
  }
}
