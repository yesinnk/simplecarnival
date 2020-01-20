package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.LevelAdapter;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.LevelRecyclerView;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.SectionBuilder;

/**
 * The binder class which is responsible for binding any elements used in the LevelManager Class
 * {@link LevelManager}
 */
public abstract class LevelManagerBinder {

  final MutableLiveData<LevelStatusEnum> levelStatusMutableLiveData;
  final MutableLiveData<InteractionEvent> interactionEventMutableLiveData;
  final View view;
  final LevelRecyclerView levelRecyclerView;
  final LevelAdapter levelAdapter;
  final LinearLayoutManager levelLayoutManager;
  ArrayList<SectionBuilder> levelDesigns;
  final LevelAnimationManager levelAnimationManager;
  int levelNumber;

  LevelManagerBinder(Context context, View view) {
    this.levelAdapter = new LevelAdapter();
    this.view = view;

    levelLayoutManager = new LinearLayoutManager(context);

    // Set Up RecyclerView
    levelRecyclerView = view.findViewById(R.id.slide_level_recycler_view);
    levelRecyclerView.setAdapter(levelAdapter);
    levelRecyclerView.setLayoutManager(levelLayoutManager);
    levelStatusMutableLiveData = new MutableLiveData<>();
    interactionEventMutableLiveData = new MutableLiveData<>();

    // Set Up Animator
    levelAnimationManager = new LevelAnimationManager(levelStatusMutableLiveData);
  }

  /**
   * Creates an live data observer that keeps track of of changes in the LevelStatus. For instance a
   * level can be completed, a new level can be loaded and the game can be completed
   *
   * @param levelEventHandler The event handler(observer) that will manage state changes of level
   *     status
   * @return A new live data observer
   */
  public Observer<LevelStatusEnum> getObserverForLevelEvents(
      final LevelEventHandler levelEventHandler) {
    return new Observer<LevelStatusEnum>() {
      @Override
      public void onChanged(LevelStatusEnum levelStatus) {
        if (levelStatus == LevelStatusEnum.LEVEL_COMPLETE) {
          levelEventHandler.onLevelComplete();
        } else if (levelStatus == LevelStatusEnum.LEVEL_NEXT) {
          levelEventHandler.onNextLevel();
        }
        if (levelStatus == LevelStatusEnum.GAME_COMPLETE) {
          levelEventHandler.onGameComplete();
        }
        if(levelStatus == LevelStatusEnum.LEVELS_LOADED){
          levelEventHandler.onLoadLevel();
        }
      }
    };
  }

  /**
   * Creates an live data observer that keeps track of of interactions of sprites and level objects.
   *
   * @param levelEventHandler The event handler(observer) that will observer interaction events
   * @return A new live data observer that handles interactionEvents
   */
  public Observer<InteractionEvent> getObserverForInteractionEvent(
      final LevelEventHandler levelEventHandler) {
    return new Observer<InteractionEvent>() {
      @Override
      public void onChanged(InteractionEvent interactionEvent) {
        levelEventHandler.onInteraction(interactionEvent);
      }
    };
  }
}
