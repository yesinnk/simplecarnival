package ca.carnivalgames.simplecarnival.animation;

/**
 * This type keyframe should be used if you require a keyframe on every frame in an animation. You
 * do not need to provide timing as this is implied
 */
public abstract class ConstantKeyFrame extends SimpleKeyFrame {
  public ConstantKeyFrame() {
    super(new KeyTime(0, 0), true);
  }

  @Override
  public void onKeyframe(int frameNumber, int totalFramesCalled) {
    onEachFrame(totalFramesCalled);
  }

  public abstract void onEachFrame(int frameNumber);
}
