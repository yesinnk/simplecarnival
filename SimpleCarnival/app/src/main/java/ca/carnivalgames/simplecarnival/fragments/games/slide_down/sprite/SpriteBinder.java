package ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite;

import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.R;

/**
 * A Binder class that binds all the sprite xml elements to given by the SpriteResource class which
 * holds resource information. It contains a List for walking sprites and sprites on vine for easy
 * animations of those groups of ImageViews
 */
class SpriteBinder {
  final SpriteAnimationManager animationManager;
  final ImageView hurt;
  SpritePositionManager positionManager;
  ArrayList<ImageView> vineComponents;
  ArrayList<ImageView> walkComponents;
  final ImageView hands;
  final ImageView blink;
  final ImageView legs;
  final ImageView body;
  final ImageView onTopOfVine;
  final ImageView walk1;
  final ImageView walk2;
  final ImageView walk3;
  final ImageView peace;

  SpriteBinder(View view, SpriteResource resource) {
    positionManager = new SpritePositionManager();
    animationManager = new SpriteAnimationManager();

    hands = view.findViewById(R.id.sprite_hands);
    blink = view.findViewById(R.id.sprite_eyes_blink);
    legs = view.findViewById(R.id.sprite_legs);
    body = view.findViewById(R.id.sprite_body);
    onTopOfVine = view.findViewById(R.id.sprite_on_top_vine);

    walk1 = view.findViewById(R.id.sprite_walk_1);
    walk2 = view.findViewById(R.id.sprite_walk_2);
    walk3 = view.findViewById(R.id.sprite_walk_3);
    peace = view.findViewById(R.id.sprite_peace);
    hurt = view.findViewById(R.id.sprite_hurt);

    body.setImageResource(resource.getBody());
    legs.setImageResource(resource.getLegs());
    blink.setImageResource(resource.getEyes());
    hands.setImageResource(resource.getHands());
    onTopOfVine.setImageResource(resource.getVineTop());
    walk1.setImageResource(resource.getWalk1());
    walk2.setImageResource(resource.getWalk2());
    walk3.setImageResource(resource.getWalk3());
    peace.setImageResource(resource.getPeace());
    hurt.setImageResource(resource.getHurt());

    vineComponents =
        new ArrayList<ImageView>() {
          {
            add(body);
            add(hands);
            add(legs);
            add(blink);
            add(hurt);
          }
        };

    walkComponents =
        new ArrayList<ImageView>() {
          {
            add(walk1);
            add(walk2);
            add(walk3);
          }
        };

    positionManager.positionSprite("LEFT", vineComponents);
  }
}
