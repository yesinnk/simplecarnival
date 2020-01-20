package ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite;

import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.animation.ConstantKeyFrame;
import ca.carnivalgames.simplecarnival.animation.KeyFrame;
import ca.carnivalgames.simplecarnival.animation.KeyTime;
import ca.carnivalgames.simplecarnival.animation.SimpleKeyFrame;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.LevelStatusEnum;

/** The Sprite animation manager store all the animations that a sprite can show */
class SpriteAnimationManager {
  private boolean isMoving = false;

  /**
   * The animation the makes the sprite look like it is climbing the vine and also animates eye
   * blinking
   *
   * @param animationEngine The animation engine used in this game
   * @param legs The image view holding the legs of the sprite
   * @param hands The image view holding the hands of the sprite
   * @param blink The image view holding the blinked eye of the sprite
   */
  void onVineAnimation(
      AnimationEngine animationEngine,
      final ImageView legs,
      final ImageView hands,
      final ImageView blink) {
    animationEngine.addKeyframe(
        "hands & legs",
        0.5f,
        new KeyFrame(new KeyTime(0, 0.25f), false) {
          @Override
          public void onDefaultFrame(int frameNumber, int totalFramesCalled) {
            legs.setTranslationY(0);
            hands.setTranslationY(0);
          }

          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            legs.setTranslationY(-2);
            hands.setTranslationY(5);
          }
        });

    List<KeyTime> blinkKeyTimeList = new ArrayList<>();
    blinkKeyTimeList.add(new KeyTime(1.5f, 1.6f));
    blinkKeyTimeList.add(new KeyTime(1.8f, 1.9f));
    animationEngine.addKeyframe(
        "blink",
        2,
        new KeyFrame(blinkKeyTimeList, false) {
          @Override
          public void onDefaultFrame(int frameNumber, int totalFramesCalled) {
            blink.setVisibility(View.INVISIBLE);
          }

          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            if (!isMoving) blink.setVisibility(View.VISIBLE);
          }
        });
  }

  /**
   * Helps animate the sprite moving from side to side by show and hiding different elements
   *
   * @param animationEngine The animation engine used in this game
   * @param onVineSpriteComponents The components of the sprite on the vine in a list
   * @param middle The image view that holds the sprite that is in the middle of the vine
   */
  void onMoveToSideAnimation(
      AnimationEngine animationEngine,
      final ArrayList<ImageView> onVineSpriteComponents,
      final ImageView middle) {
    animationEngine.addKeyframe(
        "move",
        0.14f,
        new KeyFrame(new KeyTime(0, 0.09f), false, 0) {
          @Override
          public void onDefaultFrame(int frameNumber, int totalFramesCalled) {
            for (ImageView component : onVineSpriteComponents) {
              component.setVisibility(View.VISIBLE);
            }
            middle.setVisibility(View.INVISIBLE);
            isMoving = false;
          }

          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            for (ImageView component : onVineSpriteComponents) {
              component.setVisibility(View.INVISIBLE);
            }
            middle.setVisibility(View.VISIBLE);
            isMoving = true;
          }
        });
  }

  /**
   * Show the sprite dropping down from the vine then walking and finally showing a peace sign
   *
   * @param animationEngine The animation engine used in this game
   * @param onVineSpriteComponents The components of the sprite on the vine in a list
   * @param walkSpriteComponents The components of the sprite that animate walking
   * @param peaceSprite The image view that holds the peace sprite
   * @param middle The image view that holds the sprite that is in the middle of the vine
   * @param levelStatusMutableObservable An observable that lets the game fragment know that the
   *     animation is over
   */
  void endLevelAnimation(
      final AnimationEngine animationEngine,
      final ArrayList<ImageView> onVineSpriteComponents,
      final ArrayList<ImageView> walkSpriteComponents,
      final ImageView peaceSprite,
      final ImageView middle,
      final MutableLiveData<LevelStatusEnum> levelStatusMutableObservable) {
    animationEngine.removeKeyframe("blink");
    animationEngine.removeKeyframe("move");
    animationEngine.removeKeyframe("hands & legs");
    middle.setVisibility(View.INVISIBLE);

    for (ImageView onVineSpriteComponent : onVineSpriteComponents) {
      onVineSpriteComponent.setVisibility(View.VISIBLE);
    }

    float fallenSpriteTranslationX = onVineSpriteComponents.get(0).getTranslationX();
    for (ImageView walkComponent : walkSpriteComponents) {
      walkComponent.setTranslationX(fallenSpriteTranslationX);
    }

    final float peaceSpriteYPosition = peaceSprite.getY();
    animationEngine.addKeyframe(
        "sprite falling",
        10,
        new SimpleKeyFrame(new KeyTime(0, 10), true, 0) {
          ArrayList<Float> originalYPositions = new ArrayList<>();

          {
            for (ImageView onVineSpriteComponent : onVineSpriteComponents) {
              originalYPositions.add(onVineSpriteComponent.getY());
            }
          }

          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            boolean animateFalling =
                (onVineSpriteComponents.get(0).getY() + 40) <= peaceSpriteYPosition;
            if (animateFalling)
              for (ImageView component : onVineSpriteComponents) {
                component.setY(component.getY() + 40);
              }
            else {
              for (ImageView component : onVineSpriteComponents) {
                component.setVisibility(View.INVISIBLE);
              }
              // Reset Sprite positions positions
              for (int i = 0; i < onVineSpriteComponents.size(); i++) {
                ImageView onVineSpriteComponent = onVineSpriteComponents.get(i);
                onVineSpriteComponent.setY(originalYPositions.get(i));
              }
              animationEngine.removeKeyframe("sprite falling");
              walkAndPeaceAnimation(
                  animationEngine, walkSpriteComponents, peaceSprite, levelStatusMutableObservable);
            }
          }
        });
  }

  /** The Sub sequence that shows the sprite walking */
  private void walkAndPeaceAnimation(
      final AnimationEngine animationEngine,
      final ArrayList<ImageView> walkComponents,
      final ImageView peaceSprite,
      final MutableLiveData<LevelStatusEnum> levelStatusMutableObservable) {
    int animationLength = 8;
    int frameRate = animationEngine.getFrameRate();
    final int walkAnimationLengthInFrames = (animationLength - 1) * frameRate;
    animationEngine.addKeyframe(
        "walk sequence",
        10,
        new SimpleKeyFrame(new KeyTime(0, animationLength), true, 0) {
          @Override
          public void onKeyframe(int frameNumber, int totalFramesCalled) {
            if (frameNumber < walkAnimationLengthInFrames) {
              int visibleSpriteIndex = (frameNumber % 60) / 20;
              for (int i = 0; i < walkComponents.size(); i++) {
                ImageView component = walkComponents.get(i);
                if (i == visibleSpriteIndex) component.setVisibility(View.VISIBLE);
                else component.setVisibility(View.INVISIBLE);

                if (component.getTranslationX() != 0) {
                  int increment = component.getTranslationX() < 0 ? 1 : -1;
                  float translationX = increment + component.getTranslationX();
                  component.setTranslationX(translationX);
                }
              }
            } else {
              for (ImageView components : walkComponents) {
                components.setVisibility(View.INVISIBLE);
              }
              showPeaceForTwo(animationEngine, peaceSprite, levelStatusMutableObservable);
            }
          }
        });
  }

  /** The Sub sequence that shows the sprite showing the peace sign */
  private void showPeaceForTwo(
      final AnimationEngine animationEngine,
      final View peaceSprite,
      final MutableLiveData<LevelStatusEnum> levelStatusMutableObservable) {
    animationEngine.addKeyframe(
        "peace",
        10,
        new ConstantKeyFrame() {
          int firstFrame = -1;

          @Override
          public void onEachFrame(int frameNumber) {
            if (firstFrame == -1) firstFrame = frameNumber;
            peaceSprite.setVisibility(View.VISIBLE);
            if ((frameNumber - firstFrame) > 60) {
              peaceSprite.setVisibility(View.INVISIBLE);
              levelStatusMutableObservable.setValue(LevelStatusEnum.LEVEL_NEXT);
              animationEngine.removeKeyframe("peace");
            }
          }
        });
  }

  /**
   * Shows the sprite temporarily getting hurt
   *
   * @param animationEngine The animation engine used in this game
   * @param onVineSpriteComponents The components of the sprite on the vine in a list
   * @param hurt The ImageView containing the hurt sprite
   * @param middle The ImageView containing the middle sprite
   */
  void hurtAnimation(
      final AnimationEngine animationEngine,
      final ArrayList<ImageView> onVineSpriteComponents,
      final ImageView hurt,
      final ImageView middle) {

    middle.setAlpha(0f);
    hurt.setAlpha(1f);
    for (ImageView component : onVineSpriteComponents) {
      component.setAlpha(0f);
    }
    animationEngine.addOnEachAnimationFrame(
        "sprite_hurt",
        new ConstantKeyFrame() {
          int firstFrame = -1;
          boolean isTransparent = false;

          @Override
          public void onEachFrame(int frameNumber) {
            if (firstFrame == -1) firstFrame = frameNumber;

            int currentFrame = frameNumber - firstFrame;
            if (currentFrame % 5 == 0) {
              hurt.setAlpha(isTransparent ? 1f : 0f);
              isTransparent = !isTransparent;
            }

            if (currentFrame > 60) {
              middle.setAlpha(1f);
              for (ImageView component : onVineSpriteComponents) {
                component.setAlpha(1f);
              }
              hurt.setAlpha(0f);
              animationEngine.removeKeyframe("sprite_hurt");
            }
          }
        });
  }
}
