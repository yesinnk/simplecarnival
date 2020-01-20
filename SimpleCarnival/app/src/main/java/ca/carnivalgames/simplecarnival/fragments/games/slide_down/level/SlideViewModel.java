package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import ca.carnivalgames.simplecarnival.Utils;
import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.MainLevels;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite.Sprite;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite.SpriteFactory;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.status.ScoreKeeper;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.status.StatusViewManager;

/**
 * View Model Class that aids the SlideDown Fragment
 * {@link ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideDown}
 * */
public class SlideViewModel extends ViewModel {
  private ScoreKeeper score;
  private StatusViewManager status;
  private Sprite sprite;
  private SlideLevelManager levelManager;
  private int level;

  /** Sets up some initial managers such as the Game ScoreKeeper, and the StatusViewManager
   * @param view
   * @param context
   */
  public void setUpView(View view, Context context) {
    score = new ScoreKeeper();
    status = new StatusViewManager(view);
    levelManager = LevelManager.createManager(context, view);
  }

  public void setSprite(View view, String spriteName) {
    sprite = SpriteFactory.create(spriteName, view);
  }

  public void setLevels(final MainLevels sectionBuilders) {
    Utils.Threads.runOnBackgroundThread(new Runnable() {
      @Override
      public void run() {
        levelManager.loadLevels(sectionBuilders);
        levelManager.getLevelStatusMutableObservable().postValue(LevelStatusEnum.LEVELS_LOADED);
      }
    });

  }

  public void subscribeToLevelEvents(
      LifecycleOwner lifecycleOwner, LevelEventHandler levelEventHandler) {
    levelManager
        .getLevelStatusObservable()
        .observe(lifecycleOwner, levelManager.getObserverForLevelEvents(levelEventHandler));

    levelManager
        .getInteractionEventObservable()
        .observe(lifecycleOwner, levelManager.getObserverForInteractionEvent(levelEventHandler));
  }

  public void startLevel(int level, AnimationEngine animationEngine) {
    this.level = level;
    score.startTimer();
    sprite.showOnVineSprite(animationEngine);
    score.setLevel(level);
    levelManager.setLevel(level).startVineAnimationSequence(animationEngine);
  }

  public void pauseLevel(){
    score.pauseTimer();
    levelManager.setPause(true);
  }

  public void upauseLevel(){
    score.unpauseTimer();
    levelManager.setPause(false);
  }

  public void moveSprite(String direction, AnimationEngine animationEngine) {
    if (direction.equals("right")) sprite.moveRight(animationEngine);
    else sprite.moveLeft(animationEngine);
  }

  public void handleLevelComplete(AnimationEngine animationEngine) {
    score.stopTimer();
    sprite.startEndLevelAnimation(animationEngine, (LevelManager) levelManager);
    status.showCompletedStatus(animationEngine, score, levelManager.getLevelWorstTime());
  }

  public void nextLevel(AnimationEngine animationEngine) {
    if(levelManager.hasLevel(level + 1)){
      level++;
      startLevel(level, animationEngine);
      levelManager.resetRecyclerPosition();
      sprite.setSpriteVisibilityAndInput();
      status.hideCompletedStatus();

    }else{
      levelManager.getLevelStatusMutableObservable().setValue(LevelStatusEnum.GAME_COMPLETE);
    }
  }



  public Sprite getSprite() {
    return sprite;
  }

  public ScoreKeeper getScoreKeeper() {
    return score;
  }

  public void handleInteraction(InteractionEvent interactionEvent, AnimationEngine animationEngine) {
    String position = sprite.getPosition();
    if (position.equalsIgnoreCase("LEFT")) {
      ImageView itemImageView = interactionEvent.getImageViewLeft();
      LevelItem levelItem = interactionEvent.getLevelItemLeft();
      if (levelItem != null) levelItem.interact(sprite, score, levelManager, itemImageView, animationEngine);
    } else {
      ImageView itemImageView = interactionEvent.getImageViewRight();
      LevelItem levelItem = interactionEvent.getLevelItemRight();
      if (levelItem != null) levelItem.interact(sprite, score, levelManager, itemImageView, animationEngine);
    }
  }


}
