package ca.carnivalgames.simplecarnival.fragments.games.create_a_shape;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.GameInfo;
import ca.carnivalgames.simplecarnival.fragments.structural.SimpleGameControllerFragment;

public class CatchAShape extends SimpleGameControllerFragment {
  private ImageView cup;
  private ImageView coin;
  private ImageView coin2;
  private TextView scoreLabel;
  private TextView missedLabel;
  private TextView startText;
  private AnimationEngine animationEngine;
  private CatchAShapeViewModel viewModel;

  /**
   * The initialization of the view and view-model as well as binding of the image-view objects
   * @param view The view of the game
   */
  @Override
  protected void setUpView(View view) {
    super.setUpView(view);
    if(getActivity() == null) return;
    viewModel = ViewModelProviders.of(getActivity()).get(CatchAShapeViewModel.class);
    //Initialize animationEngine
    animationEngine = new AnimationEngine();
    //Bind ImageView's
    bindObjects(view);
    //Feed labels to the view-model as well as the screen height and width
    viewModel.setLabels(scoreLabel, missedLabel,startText);
    viewModel.setScreenSize(super.getScreenWidth(), super.getScreenHeight());
  }

  /**
   * Called immediatley on creation of view and used to spin up animations
   * @param view
   * @param savedInstanceState
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    viewModel.reset(cup,coin);
  }

  /**
   * Method that receives where the user tapped the screen and moves the cup to that X value
   * @param axisValue the X axis value to which the cup should be moved
   */
  @Override
  public void onTouchXPosition(float axisValue) {
    super.onTouchXPosition(axisValue);
    if(!viewModel.isStarted()){
      viewModel.setCoinAnimation(animationEngine, coin, cup, 150, "coin1");
      //viewModel.setCoinAnimation(animationEngine, coin2, cup, 700, "coin2");
      viewModel.start();
    }
    cup.setTranslationX(axisValue);
  }

  /**
   * Method that binds all the labels and imageview objects from the layout.xml
   * @param view
   */
  private void bindObjects(View view){
      cup = view.findViewById(R.id.MiddleCup);
      coin = view.findViewById(R.id.catch_a_shape_coin);
      startText = view.findViewById(R.id.startLabel);
      scoreLabel = view.findViewById(R.id.MainLabel);
      missedLabel = view.findViewById(R.id.missLabel);
  }

  /**
   * Method called when the user backs out of the game
   */
  @Override
  protected void onGamePause() {
    //viewModel.reset(cup,coin);
  }

  /**
   *
   * @return A GameInfo object containing the score from the game, the level reached, and the game
   * coins collected
   */
  @Override
  protected GameInfo onSaveGame() {
    int coinsCollected = viewModel.getScore() / 10;
    return new GameInfo("Catch a Coin", viewModel.getScore(), viewModel.getLevel(), coinsCollected);
  }

  @Override
  protected int getLayout() {
    return R.layout.fragment_catch_a_shape;
  }

  public static String getTitle() {
    return "Catch A Coin";
  }

  @Override
  public void onDetach() {
    animationEngine.kill();
    super.onDetach();
  }


}
