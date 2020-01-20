package ca.carnivalgames.simplecarnival.animation;

import java.util.List;

public abstract class SimpleKeyFrame extends KeyFrame {
  SimpleKeyFrame(final List<KeyTime> keyTimes, boolean tween) {
    super(keyTimes, tween);
  }

  public SimpleKeyFrame(final KeyTime keyTime, boolean tween) {
    super(keyTime, tween);
  }

  public SimpleKeyFrame(final KeyTime keyTime, boolean tween, int loopTimes) {
    super(keyTime, tween, loopTimes);
  }


  @Override
  public void onDefaultFrame(int frameNumber, int totalFramesCalled) {}
}
