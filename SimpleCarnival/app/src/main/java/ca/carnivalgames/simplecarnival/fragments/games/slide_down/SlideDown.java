package ca.carnivalgames.simplecarnival.fragments.games.slide_down;

import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.GameInfo;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.InteractionEvent;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.LevelEventHandler;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.SlideViewModel;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.status.ScoreKeeper;
import ca.carnivalgames.simplecarnival.fragments.structural.SimpleGameControllerFragment;

/** The GameFragment that acts as a main point of entry to the Android SubSystem */
public class SlideDown extends SimpleGameControllerFragment implements LevelEventHandler {
  private final AnimationEngine animationEngine = new AnimationEngine();
  private SlideViewModel slideViewModel;
  private View congratulations;
  private MaterialButton slideStartButton;
  private View welcomeScreen;

  public static String getTitle() {
    return "Super Vine Bros";
  }

  @Override
  protected void onGamePause() {}

  @Override
  protected GameInfo onSaveGame() {
    ScoreKeeper scoreKeeper = slideViewModel.getScoreKeeper();
    int coinsCollected = (int) (scoreKeeper.getTotalScore() / 100);
    return new GameInfo(
        "Super Vine Bros", (int) scoreKeeper.getTotalScore(), scoreKeeper.getLevel(), coinsCollected);
  }

  @Override
  public void onDetach() {
    animationEngine.kill();
    super.onDetach();
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_slide_down;
  }

  @Override
  protected void setUpView(View view) {
    super.setUpView(view);
    congratulations = view.findViewById(R.id.slide_congratulations_layout);
    slideStartButton = view.findViewById(R.id.slide_start_button);
    welcomeScreen = view.findViewById(R.id.slide_welcome);

    if (getActivity() != null) {
      slideViewModel = ViewModelProviders.of(getActivity()).get(SlideViewModel.class);
      slideViewModel.setUpView(view, getContext());
      slideViewModel.setSprite(view, "mario");
      slideViewModel.setLevels(new MainLevels(getResources()));
      slideViewModel.subscribeToLevelEvents(getViewLifecycleOwner(), this);
    }
  }

  @Override
  public void onTouchOrDragRight() {
    slideViewModel.moveSprite("right", animationEngine);
  }

  @Override
  public void onTouchOrDragLeft() {
    slideViewModel.moveSprite("left", animationEngine);
  }

  @Override
  public void onLevelComplete() {
    slideViewModel.handleLevelComplete(animationEngine);
  }

  @Override
  public void onNextLevel() {
    Log.e("next level", "Complete");
    slideViewModel.nextLevel(animationEngine);
  }

  @Override
  public void onGameComplete() {
    congratulations.setVisibility(View.VISIBLE);
  }

  @Override
  public void onInteraction(InteractionEvent interactionEvent) {
    slideViewModel.handleInteraction(interactionEvent, animationEngine);
  }

  @Override
  public void onLoadLevel() {
    slideStartButton.setBackgroundTintList(
        ContextCompat.getColorStateList(getContext(), R.color.colorEveningSetBlue));

    slideStartButton.setText(R.string.i_m_ready);
    slideStartButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            slideViewModel.startLevel(0, animationEngine);
            welcomeScreen.setVisibility(View.GONE);
          }
        });
  }
}
