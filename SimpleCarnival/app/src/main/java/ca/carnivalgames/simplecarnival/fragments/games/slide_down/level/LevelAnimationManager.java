package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.Utils;
import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.animation.ConstantKeyFrame;
import ca.carnivalgames.simplecarnival.animation.KeyTime;
import ca.carnivalgames.simplecarnival.animation.SimpleKeyFrame;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.LevelRecyclerView;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.LevelSegment;

import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.InteractionUtils.detectInteraction;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.LevelAnimationUtils.animateItemsOnScreen;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.LevelAnimationUtils.getLevelItemsInViewHolder;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.LevelAnimationUtils.getVisibleSegmentIndices;

class LevelAnimationManager {
  private final String PLAY_GAME = "PLAY";
  private final String LEVEL_COMPLETE = "COMPLETE";
  private final MutableLiveData<LevelStatusEnum> levelStatusMutableLiveData;

  private int deltaY = 1;
  private int speedLimit = 8;
  private int prevY1 = -1;
  private boolean isPaused = false;

  LevelAnimationManager(MutableLiveData<LevelStatusEnum> levelStatusMutableLiveData) {
    this.levelStatusMutableLiveData = levelStatusMutableLiveData;
  }

  /**  The animation keyframe used to make the level scroll, animate level items and help with
   * interaction detections.
   *
   * @param animationEngine
   * @param levelRecyclerView
   * @param linearLayoutManager
   * @param levelSegments
   * @param interactionEventMutableLiveData
   */
  void onLevelStart(
      final AnimationEngine animationEngine,
      final LevelRecyclerView levelRecyclerView,
      final LinearLayoutManager linearLayoutManager,
      final ArrayList<LevelSegment> levelSegments,
      final MutableLiveData<InteractionEvent> interactionEventMutableLiveData) {

    animationEngine.addOnEachAnimationFrame(
        PLAY_GAME,
        new ConstantKeyFrame() {
          @Override
          public void onEachFrame(int frameNumber) {
            if (isPaused) return;
            // Change the recyclerView Scroll
            levelRecyclerView.scrollBy(0, deltaY);
            final int yPos = levelRecyclerView.computeVerticalScrollOffset();

            if (!levelCompleted(yPos, animationEngine)) {
              prevY1 = yPos;
              final float recyclerHeight = levelRecyclerView.getHeight();
              if (recyclerHeight == 0) return;
              handleLevelItemAnimationsAndInteractions(
                  yPos,
                  linearLayoutManager,
                  levelSegments,
                  interactionEventMutableLiveData,
                  recyclerHeight,
                  frameNumber);
              increaseLevelSpeed(frameNumber);
            } else{
              prevY1 = -1;
              deltaY = 1;
            }
          }
        });
  }

  /**
   * Animates any visible {@link
   * ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem} on screen.
   * This is important as not all views need to be drawn to the screen. The recyclerView handles the
   * garbage collection and recycling of repetitive views while this method ensure items within the
   * visible range of the recycler view is animated and interactable.
   *
   * @param yPos the position of the recyclerView scroll
   * @param linearLayoutManager the manager used to layout the recyclerView
   * @param levelSegments the data used to populate the recyclerView
   * @param interactionEventMutableLiveData the liveData use to notify subscribers of interactions
   * @param recyclerHeight the height of the recyclerView used to draw the level
   * @param frameNumber the current frame number used in the animation of the level
   */
  private void handleLevelItemAnimationsAndInteractions(
      int yPos,
      LinearLayoutManager linearLayoutManager,
      ArrayList<LevelSegment> levelSegments,
      MutableLiveData<InteractionEvent> interactionEventMutableLiveData,
      float recyclerHeight,
      int frameNumber) {
    // Get Visible Indices
    final List<Integer> visibleSegmentIndices = getVisibleSegmentIndices(yPos);

    for (int i = 0; i < visibleSegmentIndices.size(); i++) {
      Integer visibleSegmentIndex = visibleSegmentIndices.get(i);
      List<List<ImageView>> itemsInViewHolder =
          getLevelItemsInViewHolder(visibleSegmentIndex, linearLayoutManager);
      if (itemsInViewHolder != null) {
        LevelSegment levelSegment = levelSegments.get(visibleSegmentIndex);

        // Delegates Item Interaction
        detectInteraction(
            interactionEventMutableLiveData,
            yPos,
            levelSegment,
            itemsInViewHolder,
            recyclerHeight,
            i,
            speedLimit);
        // Animate at 6 frames per second
        if (frameNumber % 10 == 0) animateItemsOnScreen(levelSegment, itemsInViewHolder, 0, 3);
      }
    }
  }

  void onLevelEnd(final AnimationEngine animationEngine, View view) {
    final View blackBackground = view.findViewById(R.id.black_background);
    final int animationLength = 2;
    animationEngine.addKeyframe(
        "Fade Background",
        3,
        new SimpleKeyFrame(new KeyTime(0, animationLength), true, 0) {
          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            float opacity = covertFrameToSeconds(frameNumber) / animationLength;
            blackBackground.setAlpha(opacity);
          }
        });
  }

  private void increaseLevelSpeed(int frameNumber) {
    if (deltaY < getSpeedLimit() && frameNumber % 5 == 0) {
      deltaY++;
    }
  }

  private float getSpeedLimit() {
    return Utils.Measurement.pixelToDP(speedLimit);
  }

  private boolean levelCompleted(int nextY, AnimationEngine animationEngine) {
    if (prevY1 == nextY) {
      animationEngine.removeKeyframe(PLAY_GAME);
      levelStatusMutableLiveData.postValue(LevelStatusEnum.LEVEL_COMPLETE);
      return true;
    }
    return false;
  }

  public void setPaused(boolean paused) {
    isPaused = paused;
  }

  void resetSpeed() {
    deltaY = 5;
  }
}
