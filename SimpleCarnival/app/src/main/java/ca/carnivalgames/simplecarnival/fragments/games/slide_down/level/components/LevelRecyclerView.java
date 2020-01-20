package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A customized recyclerView that ignore touch events
 * */
public class LevelRecyclerView extends RecyclerView {

    // Standard Constructors
    public LevelRecyclerView(@NonNull Context context) {
        super(context);
    }

    public LevelRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LevelRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // Disable RecyclerView Touch Consumption
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
