package ca.carnivalgames.simplecarnival.fragments.games.cup_shuffler;

import android.animation.ObjectAnimator;
import android.animation.AnimatorSet;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.fragments.games.GameInfo;
import ca.carnivalgames.simplecarnival.fragments.structural.GamesFragment;

public class CupShuffler extends GamesFragment {

    private CupShufflerViewModel viewModel;
    private int screenWidth;

    private ImageView leftCup;
    private ImageView middleCup;
    private ImageView rightCup;
    private ImageView ball;
    private TextView streakLabel;
    private TextView highestlabel;
    private TextView coinsLabel;
    private Button startButton;


    private int firstSlot;
    private int secondSlot;
    private int thirdSlot;


    public static String getTitle() {
        return "Cup Shuffler";
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_cup_shuffler;
    }

    @Override
    protected void setUpView(final View view) {
        WindowManager wm = getActivity().getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        viewModel = ViewModelProviders.of(getActivity()).get(CupShufflerViewModel.class);
        viewModel.setScreenSize(size.x, size.y);
        screenWidth = size.x;

        //set up the slots for the cups
        firstSlot = screenWidth*2/5;
        secondSlot = screenWidth*3/5;
        thirdSlot = screenWidth*4/5;

        //connecting the image to object
        leftCup = view.findViewById(R.id.LeftCup);
        middleCup = view.findViewById(R.id.MiddleCup);
        rightCup = view.findViewById(R.id.RightCup);
        ball = view.findViewById(R.id.Ball);

        viewModel.initCup(leftCup, middleCup, rightCup, ball, firstSlot, secondSlot, thirdSlot);

        streakLabel = view.findViewById(R.id.StreakLabel);
        highestlabel = view.findViewById(R.id.highestLabel);
        coinsLabel = view.findViewById(R.id.coinsLabel);
        startButton = view.findViewById(R.id.StartButton);

        highestlabel.setText("Highest Level : " + viewModel.highestLevel());
        coinsLabel.setText("Coins Collected : " + viewModel.getCoinsCollected());


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        System.out.println("can Guess:"+viewModel.canGuess);

        // Set these up so user can guess which cup it's in.
        leftCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.guess(leftCup) && viewModel.canGuess) { // Increase streak by 1 and shuffle the cups again.
                    startNextLevel();
                } else if (viewModel.canGuess) { // Restart the game
                    restartGame();
                }
            }
        });

        rightCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.guess(rightCup) && viewModel.canGuess) {
                    startNextLevel();
                } else if (viewModel.canGuess){
                    restartGame();

                }
            }
        });

        middleCup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.guess(middleCup) && viewModel.canGuess) {
                    startNextLevel();
                } else if (viewModel.canGuess){
                    restartGame();

                }
            }
        });
    }

    /** Updates the text and coins. Then starts the next level and shuffles again*/
    private void startNextLevel() {
        streakLabel.setText("Level : " + viewModel.streakLevel());
        viewModel.shuffle(leftCup, middleCup, rightCup, ball);
        highestlabel.setText("Highest Level : " + viewModel.highestLevel());
        coinsLabel.setText("Coins Collected : " + viewModel.getCoinsCollected());
    }

    /** Brings the cup into position for the game to start*/
    private void startGame() {
        viewModel.bringCupsDown(leftCup, middleCup, rightCup);
    }

    /** Resets the entire game to the start position and brings the cups up to show ball.*/
    private void restartGame() {
        ObjectAnimator cup1Animation = ObjectAnimator.ofFloat(leftCup, "x", 0);
        ObjectAnimator cup2Animation = ObjectAnimator.ofFloat(middleCup, "x", screenWidth/3);
        ObjectAnimator cup3Animation = ObjectAnimator.ofFloat(rightCup, "x", screenWidth*2/3);
        cup1Animation.setDuration(1000);
        cup2Animation.setDuration(1000);
        cup3Animation.setDuration(1000);
        cup1Animation.start();
        cup2Animation.start();
        cup3Animation.start();
        viewModel.bringCupsUp(leftCup, middleCup, rightCup);
        streakLabel.setText("Level : " + viewModel.streakLevel());
    }

    /**
     * @param lvl: the level user is on
     * @return the number of coins the user earned on that round
     */
    private int coinsThisRound(int lvl) {
        int c = 0;
        for (int p = 0; p <= lvl; p++){
            c += p;
        }
        return c;
    }


    @Override
    protected GameInfo onSaveGame() {
        return new GameInfo("Cup Shuffler", 0, viewModel.streakLevel(), coinsThisRound(viewModel.streakLevel()));
    }

    @Override
    protected void onGamePause() {
        System.out.println("Game Paused");
    }

    static String getGameTitle() {
        return "Cup Shuffler";
    }
}
