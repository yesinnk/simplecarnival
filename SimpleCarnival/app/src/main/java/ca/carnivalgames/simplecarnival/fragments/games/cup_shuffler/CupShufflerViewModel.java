package ca.carnivalgames.simplecarnival.fragments.games.cup_shuffler;

import android.animation.ObjectAnimator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.animation.AnimatorSet;
import android.animation.Animator;
import java.util.ArrayList;



import androidx.lifecycle.ViewModel;

import java.util.Random;

public class CupShufflerViewModel extends ViewModel {
    // Game objects
    private ImageView leftCup;
    private ImageView middleCup;
    private ImageView rightCup;
    private ImageView ball;
    private ImageView hasBall;

    private int screenWidth;
    private int screenHeight;
    private int level = 1;
    private int highest = 1;
    private int coinsCollected = 0;

    boolean shuffling = false;
    boolean settingUp = false;
    boolean canGuess = false;

    /**
     * @param cup1 left cup
     * @param cup2 middle cup
     * @param cup3 right cup
     * @param ball initialise the ball
     * @param firstSlot left position
     * @param secondSlot middle position
     * @param thirdSlot right position
     * Initialises the cups' and ball's positions.
     */
    void initCup(
            ImageView cup1,
            ImageView cup2,
            ImageView cup3,
            ImageView ball,
            int firstSlot,
            int secondSlot,
            int thirdSlot) {
        leftCup = cup1;
        leftCup.setLeft(firstSlot);
        leftCup.setY(-200);

        middleCup = cup2;
        middleCup.setLeft(secondSlot);
        middleCup.setY(-200);

        rightCup = cup3;
        rightCup.setLeft(thirdSlot);
        rightCup.setY(-200);

        this.ball = ball;
        this.ball.setY(180);

        hasBall = middleCup;
    }

    /**
     * @param cup1 1st cup
     * @param cup2 2nd cup
     * @param ball if ball is with one of the cups, ball also moves
     * @param rand the number from shuffle()
     * @return the set of animations which switches the cups' positions
     */
    // Switches the two cups' positions
    private AnimatorSet moveCup(ImageView cup1, ImageView cup2, ImageView ball, int rand) {

        float cup1X = cup1.getX();
        float cup2X = cup2.getX();
        Animator cupAnimation1 = ObjectAnimator.ofFloat(cup1, "x", cup2X);
        Animator cupAnimation2 = ObjectAnimator.ofFloat(cup2, "x", cup1X);
        cupAnimation1.setDuration(1000/level);
        cupAnimation2.setDuration(1000/level);
        AnimatorSet sets = new AnimatorSet();

        if (hasBall == cup1){
            Animator ballAnimation = ObjectAnimator.ofFloat(ball, "x", cup2X);
            ballAnimation.setDuration(1000/level);
            sets.playTogether(cupAnimation1, cupAnimation2, ballAnimation);
            ball.setX(cup2X);

        } else if (hasBall == cup2){
            Animator ballAnimation = ObjectAnimator.ofFloat(ball, "x", cup1X);
            ballAnimation.setDuration(1000/level);
            sets.playTogether(cupAnimation1, cupAnimation2, ballAnimation);
            ball.setX(cup1X);
        } else {
            sets.playTogether(cupAnimation1, cupAnimation2);

        }

//
//    if (rand == 1 || rand == 2) {
//        Animator ballAnimation = ObjectAnimator.ofFloat(ball, "x", cup1X);
//        ballAnimation.setDuration(1000/level);
//        sets.playTogether(cupAnimation1, cupAnimation2, ballAnimation);
//        ball.setX(cup1X);
//    }
//    else{
//    }

        cup1.setX(cup2X);
        cup2.setX(cup1X);

        return sets;
    }

    /**
     * @param cup1 left cup
     * @param cup2 middle cup
     * @param cup3 right cup
     * @param ball with middle cup
     * Shuffles all three cups certain number of times depending on the level.
     */
    void shuffle(ImageView cup1, ImageView cup2, ImageView cup3, ImageView ball) {

        if (!settingUp){

            AnimatorSet setAnimator = new AnimatorSet();
            ArrayList<Animator> animationSet = new ArrayList<Animator>();

            Random random = new Random();
            for (int i = 0; i < level * 5; i++) {
                int randomInteger = random.nextInt(3);
                if (randomInteger == 1) {
                    animationSet.add(moveCup(cup1, cup2, ball, randomInteger));
                } else if (randomInteger == 2) {
                    animationSet.add(moveCup(cup3, cup2, ball, randomInteger));
                } else {
                    animationSet.add(moveCup(cup1, cup3, ball, randomInteger));
                }
            }

            Animator lastAnimation = animationSet.get(animationSet.size() - 1);
            Animator firstAnimation = animationSet.get(0);
            System.out.println(lastAnimation);

            firstAnimation.addListener(
                    new Animator.AnimatorListener(){
                        @Override
                        public void onAnimationStart(Animator animator) {
                            shuffling = true;
                            canGuess = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {

                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });

            lastAnimation.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            canGuess = true;
                            shuffling = false;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });

            setAnimator.playSequentially(animationSet);
            setAnimator.setDuration(2000 / level);
            setAnimator.start();
        }
                hasBall = cup2;

    }

    /** @return Streak level of the user */
    int streakLevel() {
        return level;
    }

    /** @return The highest level user has reached*/
    int highestLevel() {
        return highest; }

    /** @return The number of coins user has gained while playing*/
    int getCoinsCollected() {
        return coinsCollected; }

    /**
     * @param cup The cup that was tapped
     * @return If the guess is right or not
     * Updates the required attributes if guess is right and resets if guess is wrong.
     */
    boolean guess(ImageView cup) {
        if (shuffling == false){

            if (cup.equals(hasBall)){
                level++;
                coinsCollected += level;
                if (highest < level){
                    highest = level;
                }
                return true;
            }
            else{
                level = 1;
                return false;
            }
        }
        return false;

    }

    /**
     * @param width screen width
     * @param height screen height
     */
    void setScreenSize(int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    /**
     * @param cup1 left cup
     * @param cup2 middle cup
     * @param cup3 right cup
     * Brings the three cups down for the game to start
     */
    void bringCupsDown(final ImageView cup1, final ImageView cup2, final ImageView cup3) {
        if (!shuffling){

            settingUp = true;
            ObjectAnimator cup1Animation = ObjectAnimator.ofFloat(cup1, "y", 400);
            ObjectAnimator cup2Animation = ObjectAnimator.ofFloat(cup2, "y", 400);
            ObjectAnimator cup3Animation = ObjectAnimator.ofFloat(cup3, "y", 400);

            cup1Animation.setDuration(1000);
            cup2Animation.setDuration(1000);
            cup3Animation.setDuration(1000);

            cup1Animation.start();
            cup2Animation.start();
            cup3Animation.start();
            cup3Animation.addListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            settingUp = false;
                            shuffle(cup1,cup2,cup3,ball);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}

                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
        }

    }
    /**
     * @param cup1 left cup
     * @param cup2 middle cup
     * @param cup3 right cup
     * Brings the three cups up and shows the ball
     */
    void bringCupsUp(ImageView cup1, ImageView cup2, ImageView cup3){
        if (!shuffling){
            ObjectAnimator cup1Animation = ObjectAnimator.ofFloat(cup1, "y", -50);
            ObjectAnimator cup2Animation = ObjectAnimator.ofFloat(cup2, "y", -50);
            ObjectAnimator cup3Animation = ObjectAnimator.ofFloat(cup3, "y", -50);

            cup1Animation.setDuration(1000);
            cup2Animation.setDuration(1000);
            cup3Animation.setDuration(1000);

            cup1Animation.start();
            cup2Animation.start();
            cup3Animation.start();
        }

    }
}
