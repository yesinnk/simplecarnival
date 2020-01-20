package ca.carnivalgames.simplecarnival.animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Keyframes are used to define distinct points in animation. This animation keyframes are special
 * in the sense that they contain two key frames: A default keyframe and a normal keyframe. This is
 * handy for back and forth animations, such as sprite animations. If you need smooth keyframes make
 * sure to set tweening to true
 */
public abstract class KeyFrame {
  static final int LOOP_INCOMPLETE = 490;
  static final int LOOP_COMPLETE = 813;
  static final int LOOP_COMPLETE_RENDER_LAST = 543;
  static final int DEFAULT = 50;
  static final int KEYFRAME = 93;

  private int lastRenderedFrameType = -1;
  private int lastRenderedFrame = -1;
  private int firstFrame = -1;

  private String timelineName;

  private final int loopTimes;
  private final boolean tweening;
  private final List<KeyTime> keyTimes;

  protected KeyFrame(final List<KeyTime> keyTimes, boolean tweening) {
    this(keyTimes, tweening, -1);
  }

  protected KeyFrame(final KeyTime keyTime, boolean tweening) {
    this(
        new ArrayList<KeyTime>() {
          {
            add(keyTime);
          }
        },
        tweening);
  }

  protected KeyFrame(final KeyTime keyTime, boolean tweening, int loopTimes) {
    this(
        new ArrayList<KeyTime>() {
          {
            add(keyTime);
          }
        },
        tweening,
        loopTimes);
  }

  protected KeyFrame(final List<KeyTime> keyTimes, boolean tweening, int loopTimes) {
    this.loopTimes = loopTimes;
    this.keyTimes = keyTimes;
    this.tweening = tweening;
  }

  List<KeyTime> getKeyTimes() {
    return keyTimes;
  }

  String getTimelineName() {
    return timelineName;
  }

  void setTimelineName(String timelineName) {
    this.timelineName = timelineName;
  }

  boolean requestAnimationFrame(int frameType, int totalFramesCalled) {
    lastRenderedFrame = totalFramesCalled;
    if (tweening) return true;
    if ((frameType == KEYFRAME && lastRenderedFrameType != KEYFRAME)
        || (frameType == DEFAULT && lastRenderedFrameType != DEFAULT)) {
      lastRenderedFrameType = frameType;
      return true;
    }
    return false;
  }

  int getCurrentFrame(int totalFramesCalled, int timelineLength) {
    if (firstFrame == -1) {
      firstFrame = totalFramesCalled;
    }
    return (totalFramesCalled - firstFrame) % timelineLength;
  }

  /**
   * Helps the animation engine determine whether the loop has ended and whether the loop has ended
   * with the last frame called. This ensure clients that the last frame is always called.
   *
   * @param totalFramesCalled The total frames rendered independent of all timelines
   * @param timelineDurationInFrames The duration of the current timeline that the keyframe exists
   * @return The state of whether the loop has ended with last frame called
   */
  int getLoopState(int totalFramesCalled, int timelineDurationInFrames) {
    if (timelineDurationInFrames == 0 || loopTimes < 0) return LOOP_INCOMPLETE;
    // firstFrame KeyFrame was drawn + 1 full timeline length and additional loops of the timeline
    int finalFrame = firstFrame + (loopTimes + 1) * timelineDurationInFrames;
    //    return totalFramesCalled < finalFrame;
    int loopCount = (totalFramesCalled - firstFrame) / timelineDurationInFrames;
    if (loopCount > loopTimes) {
      if (lastRenderedFrame >= finalFrame) return LOOP_COMPLETE;
      else return LOOP_COMPLETE_RENDER_LAST;
    } else return LOOP_INCOMPLETE;
  }

  /**
   * This type of keyframe represents the default keyframe in this timeline.
   *
   * @param frameNumber The current frame number in the timeline
   * @param totalFramesCalled The total frames rendered independent of all timelines
   */
  public abstract void onDefaultFrame(int frameNumber, int totalFramesCalled);

  protected float covertFrameToSeconds(int frameNumber) {
    return ((float) frameNumber) / 60;
  }

  public abstract void onKeyframe(int frameNumber, int totalFramesCalled);
}
