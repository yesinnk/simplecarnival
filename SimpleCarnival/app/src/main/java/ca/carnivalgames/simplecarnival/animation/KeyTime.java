package ca.carnivalgames.simplecarnival.animation;

/**
 * Key times are used to the represent the start and finish of individual keyframes
 * */
public class KeyTime {
  private final float start;
  private final float stop;

  public KeyTime(float start, float stop) {
    this.start = start;
    this.stop = stop;
  }

  public float getStart() {
    return start;
  }

  public float getStop() {
    return stop;
  }
}
