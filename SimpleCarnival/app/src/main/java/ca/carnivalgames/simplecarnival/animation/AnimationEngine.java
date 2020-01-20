package ca.carnivalgames.simplecarnival.animation;

import android.animation.ValueAnimator;
import android.util.SparseArray;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The AnimationEngine is a class that can help make android animation easier by focusing strictly
 * on keyframe programming. Once AnimationEngine is instantiated an ValueAnimator looper is
 * automatically started
 */
public class AnimationEngine {
  private final int ON_EVERY_FRAME = 464;
  private int frameRate;
  private final ValueAnimator animator;
  private Map<String, KeyFrameMapMeta> nameToKeyframeMapIndices = new HashMap<>();
  private SparseArray<SparseArray<KeyFrame>> timelineKeyFrameSparseArray = new SparseArray<>();

  /**
   * This Constructor initiates the main value animator listener that will be used for all animation
   * callbacks
   */
  AnimationEngine(int frameRate) {
    this.frameRate = frameRate;
    this.animator = ValueAnimator.ofInt(0, frameRate);
    animator.setRepeatCount(ValueAnimator.INFINITE);
    animator.setInterpolator(new LinearInterpolator());
    long CYCLE_SPEED = 1000L;
    animator
        .setDuration(CYCLE_SPEED)
        .addUpdateListener(
            new ValueAnimator.AnimatorUpdateListener() {
              @Override
              public void onAnimationUpdate(ValueAnimator valueAnimator) {
                // Check if valueAnimator ready
                if (valueAnimator.getCurrentPlayTime() != 0) {
                  handleAnimationUpdate(valueAnimator);
                }
              }
            });

    animator.start();
  }

  public AnimationEngine() {
    this(60);
  }

  /**
   * This method will add this keyframe to every single frame in animation
   *
   * @param name Name of the keyframe. e.g. "sprite hands"
   * @param keyFrame The keyframe object that can be value animated.
   */
  public void addOnEachAnimationFrame(String name, ConstantKeyFrame keyFrame) {
    addKeyframe(name, ON_EVERY_FRAME, keyFrame);
  }

  /**
   * This method is used to stop the animation engine when it is no longer need. In order to use the
   * animation engine again you will need to create a new instance
   */
  public void kill() {
    animator.cancel();
    timelineKeyFrameSparseArray.clear();
    nameToKeyframeMapIndices.clear();
  }

  /**
   * This method is called on each new frame of the animation (60fps or given frameRate). Its main
   * task is to get all key frames in all timelines and send each keyframe to be requested for
   * animation
   *
   * @see {@link KeyFrame}
   * @param valueAnimator
   */
  private void handleAnimationUpdate(ValueAnimator valueAnimator) {
    // This value is between 0-60(or given frameRate) in any give second since valueAnimator started
    int currentFrameInLooper = (int) valueAnimator.getAnimatedValue();

    // The total amount of frames called since the start of the animation
    int secondsElapsed = (int) (valueAnimator.getCurrentPlayTime() / 1000);
    int totalFramesCalled = currentFrameInLooper + secondsElapsed * frameRate;

    // For each KeyFrame in each timeline, check if keyframe needs to render
    for (int i = 0; i < timelineKeyFrameSparseArray.size(); i++) {

      int timelineLength = timelineKeyFrameSparseArray.keyAt(i);
      // int currentFrameInTimeline = totalFramesCalled % timelineLength;

      // Request all keyframes in timeline to be rendered
      SparseArray<KeyFrame> keyFramesInTimeline = timelineKeyFrameSparseArray.get(timelineLength);

      ArrayList<String> keyFrameRemoveList = new ArrayList<>();

      for (int index = 0; index < keyFramesInTimeline.size(); index++) {
        int key = keyFramesInTimeline.keyAt(index);
        KeyFrame keyFrame = keyFramesInTimeline.get(key);

        int currentFrameInTimeline = keyFrame.getCurrentFrame(totalFramesCalled, timelineLength);

        // Check if frame has ended
        int loopState = keyFrame.getLoopState(totalFramesCalled, timelineLength);
        if (!(loopState == KeyFrame.LOOP_INCOMPLETE)) {
          if (loopState == KeyFrame.LOOP_COMPLETE_RENDER_LAST)
            requestFrameRender(keyFrame, timelineLength, totalFramesCalled, true);
          keyFrameRemoveList.add(keyFrame.getTimelineName());
        }
        // Request new frame
        else requestFrameRender(keyFrame, currentFrameInTimeline, totalFramesCalled, false);
      }

      for (String keyFrameName : keyFrameRemoveList) removeKeyframe(keyFrameName);
    }
  }

  /**
   * Check is keyframes or default frames need to painted on screen. If true the keyframe listener
   * methods onDefaultFrame and onKeyFrame will be called
   *
   * @param keyFrame The specific keyframe to could be rendered
   * @param currentFrameInTimeline the current frame in the timeline where the keyframe belongs
   * @param totalFramesCalled the total frames called in AnimationEngine
   */
  private void requestFrameRender(
          KeyFrame keyFrame, int currentFrameInTimeline, int totalFramesCalled, boolean isForced) {

    for (KeyTime keyTime : keyFrame.getKeyTimes()) {
      int startTime = (int) (keyTime.getStart() * frameRate);
      int stopTime = (int) (keyTime.getStop() * frameRate);

      // Check if current frame in timeline is within keyframe or default frame
      if (startTime <= currentFrameInTimeline && currentFrameInTimeline <= stopTime) {
        if (isForced || keyFrame.requestAnimationFrame(KeyFrame.KEYFRAME, totalFramesCalled)) {
          keyFrame.onKeyframe(currentFrameInTimeline, totalFramesCalled);
        }
        return;
      }
    }
    if (isForced || keyFrame.requestAnimationFrame(KeyFrame.DEFAULT, totalFramesCalled))
      keyFrame.onDefaultFrame(currentFrameInTimeline, totalFramesCalled);
  }

  /**
   * Adds keyframes to the AnimationEngine.
   *
   * @param name Name of the keyframe. e.g. "sprite hands"
   * @param timelineDuration The duration in seconds of the timeline your keyframe belongs
   * @param keyFrame The keyframe object that can be value animated.
   */
  public void addKeyframe(
      @NonNull final String name, float timelineDuration, @NonNull KeyFrame keyFrame) {
    // If keyframe name already exist, the new keyframe will not be added.
    if (nameToKeyframeMapIndices.get(name) != null) return;

    int timelineDurationInFrames;
    keyFrame.setTimelineName(name);

    if (timelineDuration == ON_EVERY_FRAME) {
      timelineDurationInFrames = 1;
    } else {
      timelineDurationInFrames = (int) (timelineDuration * frameRate);
    }

    if (timelineKeyFrameSparseArray.get(timelineDurationInFrames) == null)
      timelineKeyFrameSparseArray.put(timelineDurationInFrames, new SparseArray<KeyFrame>());

    // Null-Check in the case where sparse array has an insertion error
    SparseArray<KeyFrame> keyframesInTimeline =
        timelineKeyFrameSparseArray.get(timelineDurationInFrames);
    if (keyframesInTimeline != null) {
      int index = keyframesInTimeline.size() + 1;
      keyframesInTimeline.put(index, keyFrame);
      AnimationEngine.KeyFrameMapMeta keyFrameMapMeta =
          new AnimationEngine.KeyFrameMapMeta(timelineDurationInFrames, index);
      nameToKeyframeMapIndices.put(name, keyFrameMapMeta);
    }
  }

  /**
   * Removes keyframes from the AnimationEngine by the name it was added
   *
   * @param name
   */
  public void removeKeyframe(@NonNull String name) {
    AnimationEngine.KeyFrameMapMeta keyFrameMapMeta = nameToKeyframeMapIndices.get(name);
    if (keyFrameMapMeta != null) {
      SparseArray<KeyFrame> animationList =
          timelineKeyFrameSparseArray.get(keyFrameMapMeta.getMapKey());
      animationList.remove(keyFrameMapMeta.getListIndex());
      nameToKeyframeMapIndices.remove(name);
    }
  }

  public int getFrameRate() {
    return frameRate;
  }

  /**
   * A helper class that helps manage adding, removing and calling the keyframes in the animation
   * engine.
   */
  private class KeyFrameMapMeta {

    private final int mapKey;
    private final int listIndex;

    private KeyFrameMapMeta(int mapKey, int listIndex) {
      this.mapKey = mapKey;
      this.listIndex = listIndex;
    }

    int getMapKey() {
      return mapKey;
    }

    int getListIndex() {
      return listIndex;
    }
  }
}
