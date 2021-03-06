package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types;

import android.widget.ImageView;

import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.animation.AnimationEngine;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.SlideLevelManager;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite.Sprite;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.status.ScoreKeeper;

/**
 * A Big coin Item is like a {@link CoinItem} but is worth 10 times the amount
 * */
public class BigCoinItem extends LevelItemAnimator implements LevelItem {
    private boolean useFinalResource = false;

    /** The drawable resources used when this coin appears */
    private int[] resourceIDs = {
            R.drawable.slide_coin_00000,
            R.drawable.slide_coin_00001,
            R.drawable.slide_coin_00002,
            R.drawable.slide_coin_00003
    };

    /** The drawable resources used when this coin disappears */
    private int[] finalResourceIDs = {
            R.drawable.slide_collect_00000,
            R.drawable.slide_collect_00002,
            R.drawable.slide_empty_0000
    };

    /** See {@link LevelItem#interact} */
    @Override
    public void interact(Sprite sprite, ScoreKeeper score, SlideLevelManager levelManager, ImageView itemImageView, AnimationEngine animationEngine) {
        useFinalResource = true;
        setFinalAnimation();
        score.increaseBy(10);
    }

    /** See {@link LevelItem#getNextResourceID()} */
    @Override
    public int[] getResourceIDs() {
        return useFinalResource? finalResourceIDs : resourceIDs;
    }

    /** See {@link LevelItem#getSize()} */
    @Override
    public int getSize() {
        return 70;
    }
}
