package ca.carnivalgames.simplecarnival.fragments.menu;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import ca.carnivalgames.simplecarnival.ControllerActivity;
import ca.carnivalgames.simplecarnival.R;
import ca.carnivalgames.simplecarnival.ViewController;
import ca.carnivalgames.simplecarnival.fragments.settings.SettingsFragment;
import ca.carnivalgames.simplecarnival.fragments.structural.GamesFragment;

// Todo: Documentation Temporary Code
public class MenuFragment extends Fragment implements MenuItemClickDelegate {
  private MenuViewModel viewModel;
  private LinearLayoutManager layoutManager;
  private View layoutBackground;

  private int[] tintList = {
    Color.argb(0, 0, 0, 0),
    Color.argb(60, 200, 0, 255),
    Color.argb(60, 20, 200, 255),
    Color.argb(60, 200, 20, 55)
  };

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_menu, container, false);

    Activity activity = getActivity();
    assert activity != null;

    layoutBackground = view.findViewById(R.id.menu_bg);

    viewModel = ViewModelProviders.of(getActivity()).get(MenuViewModel.class);
    setUpRecycleView(view);
    setUpListeners(view);

    return view;
  }

  private void setUpListeners(View view) {
    Button settings = view.findViewById(R.id.menu_settings_button);
    settings.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            ViewController controller = (ViewController) getActivity();
            if (controller != null) {
              controller.loadFragment(new SettingsFragment());
            }
          }
        });
  }

  private void setUpRecycleView(View view) {
    MenuAdapter menuAdapter = new MenuAdapter(this);
    RecyclerView recyclerView = view.findViewById(R.id.menu_recycler);
    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

    recyclerView.setAdapter(menuAdapter);
    recyclerView.setLayoutManager(layoutManager);
    menuAdapter.submitList(Menu.getItemList());
    recyclerView.scrollToPosition(viewModel.getRecyclerPosition());
    PagerSnapHelper snapHelper = new PagerSnapHelper();
    snapHelper.attachToRecyclerView(recyclerView);
    recyclerViewScrollListener(recyclerView);
  }

  private void recyclerViewScrollListener(RecyclerView recyclerView) {
    recyclerView.addOnScrollListener(
        new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
              int position = layoutManager.findFirstVisibleItemPosition();
              int lastColor = ((ColorDrawable) layoutBackground.getBackground()).getColor();
              ObjectAnimator animator =
                  ObjectAnimator.ofArgb(
                      layoutBackground, "backgroundColor", lastColor, tintList[position]);
              animator.setDuration(500);
              animator.setInterpolator(new AccelerateDecelerateInterpolator());
              animator.start();
              viewModel.setRecyclerPosition(position);
            }
          }
        });
  }

  @Override
  public void onMenuItemClick(MenuItem menuItem, int index) {
    try {
      Object game = menuItem.getItemClass().newInstance();
      if (game instanceof GamesFragment) {
        ControllerActivity controller = (ControllerActivity) getActivity();
        if (controller != null) {

          controller.loadFragment((Fragment) game);
        }
      } else
        throw new IllegalArgumentException(
            "Menu.addGame: added Class that is not an instance of GamesFragment");
    } catch (IllegalAccessException | java.lang.InstantiationException e) {
      e.printStackTrace();
    }
  }
}
