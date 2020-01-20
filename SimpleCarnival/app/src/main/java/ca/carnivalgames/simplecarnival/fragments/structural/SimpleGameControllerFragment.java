package ca.carnivalgames.simplecarnival.fragments.structural;

import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public abstract class SimpleGameControllerFragment extends GamesFragment {
  private boolean controlsAreDisabled = false;
  private int halfWidth;
  private int halfHeight;

  @Override
  protected void setUpView(View view) {
    if (!setUpDisplayDimensions()) return;
    view.setOnTouchListener(
        new View.OnTouchListener() {
          @Override
          public boolean onTouch(View view, MotionEvent motionEvent) {
            return handleTouchEvents(motionEvent);
          }
        });
  }

  private boolean setUpDisplayDimensions() {
    if (getActivity() != null) {
      Display display = getActivity().getWindowManager().getDefaultDisplay();
      Point size = new Point();
      display.getSize(size);
      halfWidth = size.x / 2;
      halfHeight = size.y / 2;
    }

    return halfWidth > 0 && halfHeight > 0;
  }

  private boolean handleTouchEvents(MotionEvent motionEvent) {
    if (controlsAreDisabled) return false;
    float x = motionEvent.getAxisValue(0);
    float y = motionEvent.getAxisValue(1);

    onTouchXPosition(x);
    onTouchYPosition(y);

    if (x < halfWidth) onTouchOrDragLeft();
    else onTouchOrDragRight();

    if (y < halfHeight) onTouchOrDragTop();
    else onTouchOrDragBottom();

    return true;
  }

  public void disableController(boolean controlsAreDisabled) {
    this.controlsAreDisabled = controlsAreDisabled;
  }

  public void onTouchYPosition(float axisValue) {}

  public void onTouchXPosition(float axisValue) {}

  public void onTouchOrDragLeft() {}

  public void onTouchOrDragRight() {}

  public void onTouchOrDragTop() {}

  public void onTouchOrDragBottom() {}

  public int getScreenWidth() { return halfWidth*2; }

  public int getScreenHeight() {return halfHeight*2; }


}
