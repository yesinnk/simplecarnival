package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.SectionBuilder;

public class LevelManager extends LevelManagerBinder implements SlideLevelManager {
  private LevelManager(Context context, View view) {
    super(context, view);
  }

  public static SlideLevelManager createManager(Context context, View view) {
    return new LevelManager(context, view);
  }

  @Override
  public SlideLevelManager setLevel(int levelNumber) {
    this.levelNumber = levelNumber;
    assert levelDesigns != null;
    if (levelNumber < levelDesigns.size()) levelAdapter.submitList(levelDesigns.get(levelNumber));
    return this;
  }

  @Override
  public void loadLevels(ArrayList<SectionBuilder> levelDesigns) {
    this.levelDesigns = levelDesigns;
  }

  /**
   * Delegated Animation work to {@link LevelAnimationManager#onLevelStart}
   *
   * @param animationEngine The animation api used to render animations for the game
   */
  @Override
  public void startVineAnimationSequence(AnimationEngine animationEngine) {
    levelAnimationManager.onLevelStart(
        animationEngine,
        levelRecyclerView,
        levelLayoutManager,
        levelDesigns.get(levelNumber),
        interactionEventMutableLiveData);
  }

  @Override
  public void setPause(boolean isPaused) {
    levelAnimationManager.setPaused(isPaused);
  }

  @Override
  public void startEndLevelSequence(AnimationEngine animationEngine) {
    levelAnimationManager.onLevelEnd(animationEngine, view);
  }

  @Override
  public LiveData<LevelStatusEnum> getLevelStatusObservable() {
    return levelStatusMutableLiveData;
  }

  public MutableLiveData<LevelStatusEnum> getLevelStatusMutableObservable() {
    return levelStatusMutableLiveData;
  }
  @Override
  public LiveData<InteractionEvent> getInteractionEventObservable() {
    return interactionEventMutableLiveData;
  }

  @Override
  public void resetSpeed() {
    levelAnimationManager.resetSpeed();
  }

  @Override
  public int getLevelWorstTime() {
    return levelDesigns.get(levelNumber).getLevelWorstTime();
  }

  @Override
  public boolean hasLevel(int i) {
    return i < levelDesigns.size();
  }

  @Override
  public void resetRecyclerPosition() {
    levelRecyclerView.scrollTo(0,0);
  }
}
