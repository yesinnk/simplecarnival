package ca.carnivalgames.simplecarnival.fragments.games.petting_zoo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.games.GameInfo;
import ca.carnivalgames.simplecarnival.fragments.structural.GamesFragment;
public class PettingZoo extends GamesFragment {

  private ImageButton animal;
  private Button startButton;
  private TextView clickCounter, totalClickCounter, zooTimer, coinsCollected, levelView, instructions, gameStatus, totalCoinsCollected;
  private PettingZooViewModel viewModel;

  public static String getTitle() {
        return "Petting Zoo";
    }

  @Override
  protected int getLayout() {
      return R.layout.fragment_petting_zoo;
  }

    /**
     * The initialization of the view and view-model as well as binding of the image-view objects.
     * Sets behaviour on click of the buttons in the view.
     * @param view The view of the game
     */
  @Override
  protected void setUpView(View view) {
      viewModel = ViewModelProviders.of(getActivity()).get(PettingZooViewModel.class);

      //bind zoo objects
      animal = view.findViewById(R.id.animal);
      clickCounter = view.findViewById(R.id.click_counter);
      totalClickCounter = view.findViewById(R.id.total_click_counter);
      zooTimer = view.findViewById(R.id.timer);
      startButton = view.findViewById(R.id.start_button);
      coinsCollected = view.findViewById(R.id.coins);
      totalCoinsCollected = view.findViewById(R.id.total_coins);
      levelView = view.findViewById(R.id.level);
      instructions = view.findViewById(R.id.zoo_instructions);
      gameStatus = view.findViewById(R.id.game_status);

      animal.setBackgroundResource(R.drawable.pig);
      animal.setEnabled(false);
      instructions.setVisibility(View.VISIBLE);
      gameStatus.setVisibility(View.INVISIBLE);
      String timeLeftMessage = getString(R.string.petting_zoo_timer, 30);
      zooTimer.setText(timeLeftMessage);

      viewModel.setCurrentClicks(0);
      viewModel.setCoins(0);

      updateText();

      final CountDownTimer timer = new CountDownTimer(30000, 1000) {
          @Override
          public void onTick(long millisUntilFinished) {
              String timeLeftMessage = getString(R.string.petting_zoo_timer, millisUntilFinished / 1000);
              zooTimer.setText(timeLeftMessage);
          }

          @Override
          public void onFinish() {
              animal.setEnabled(false);
              startButton.setEnabled(true);
              gameStatus.setVisibility(View.VISIBLE);
              viewModel.levelUp(gameStatus, getString(R.string.zoo_win), getString(R.string.zoo_lose));
          }
      };

      startButton.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
              viewModel.setCurrentClicks(0);
              viewModel.setCoins(0);
              viewModel.setAngryPig(false);
              updateText();
              animal.setBackgroundResource(R.drawable.pig);
              animal.setEnabled(true);
              startButton.setEnabled(false);
              timer.start();
              instructions.setVisibility(View.INVISIBLE);
              gameStatus.setVisibility(View.INVISIBLE);
          }
      });

      animal.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View view){
              if (viewModel.isAngryPig()){
                  animal.setEnabled(false);
                  startButton.setEnabled(true);
                  timer.cancel();
                  gameStatus.setVisibility(View.VISIBLE);
                  gameStatus.setText(R.string.zoo_lose);
              }
              else {
                  viewModel.onAnimalPet();
                  viewModel.generateCoins();
                  updateText();
                  becomeAngryPig();
              }
          }
      });
  }

    /**
     *Method that makes pig angry then sets the animal button in view to the image of angry pig.
     *Sets the animal button to happy pig after 1 second and makes pig happy again.
     */
  private void becomeAngryPig(){
        viewModel.becomeAngry();
        if (viewModel.isAngryPig()){
            animal.setBackgroundResource(R.drawable.angrypig);
            final CountDownTimer timer2 = new CountDownTimer(1000, 500) {

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    viewModel.becomeHappy();
                    animal.setBackgroundResource(R.drawable.pig);
                }
            };
            timer2.start();
        }
  }

    /**
     * Updates the text displayed in the view to keep track of scores.
     */
  private void updateText(){
      String clicks = getString(R.string.petting_zoo_click_count, viewModel.getCurrentClicks());
      clickCounter.setText(clicks);
      String totalClicks = getString(R.string.petting_zoo_total_clicks, viewModel.getTotalClicks());
      totalClickCounter.setText(totalClicks);
      String coins = getString(R.string.zoo_coins, viewModel.getCoins());
      coinsCollected.setText(coins);
      String totalCoins = getString(R.string.zoo_total_coins, viewModel.getTotalCoins());
      totalCoinsCollected.setText(totalCoins);
      String level = getString(R.string.zoo_level, viewModel.getLevel());
      levelView.setText(level);
  }

  @Override
  protected void onGamePause() {}

    /**
     * Method that saves the game.
     * @return A GameInfo object containing the score from the game, the level reached, and the game
     */
  protected GameInfo onSaveGame(){
        return new GameInfo("Petting Zoo", viewModel.getTotalClicks(), viewModel.getTotalCoins(), viewModel.getLevel());
  }

}
