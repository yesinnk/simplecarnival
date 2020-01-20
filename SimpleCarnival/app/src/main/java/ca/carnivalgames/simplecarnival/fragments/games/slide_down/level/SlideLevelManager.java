package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.SectionBuilder;

public interface SlideLevelManager {
  SlideLevelManager setLevel(int levelNumber);

  void loadLevels(ArrayList<SectionBuilder> levelDesigns);

  void startVineAnimationSequence(AnimationEngine animationEngine);

  void setPause(boolean isPaused);

  void startEndLevelSequence(AnimationEngine animationEngine);

  LiveData<LevelStatusEnum> getLevelStatusObservable();

  LiveData<InteractionEvent> getInteractionEventObservable();

  Observer<LevelStatusEnum> getObserverForLevelEvents(LevelEventHandler levelEventHandler);

  Observer<InteractionEvent> getObserverForInteractionEvent(LevelEventHandler levelEventHandler);

  MutableLiveData<LevelStatusEnum> getLevelStatusMutableObservable();

  void resetSpeed();

  int getLevelWorstTime();

  boolean hasLevel(int i);

  void resetRecyclerPosition();
}
