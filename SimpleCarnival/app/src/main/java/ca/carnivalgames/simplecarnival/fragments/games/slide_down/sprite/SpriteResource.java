package ca.carnivalgames.simplecarnival.fragments.games.slide_down.sprite;

/**
 * A class representing all the {@link android.graphics.drawable.Drawable} elements that make up a
 * sprite for this game
 */
class SpriteResource {
  private final int eyes, legs, hands, body, vineTop, walk1, walk2, walk3, peace, hurt;

  SpriteResource(
      int eyes,
      int legs,
      int hands,
      int body,
      int vineTop,
      int walk1,
      int walk2,
      int walk3,
      int peace,
      int hurt) {
    this.eyes = eyes;
    this.legs = legs;
    this.hands = hands;
    this.body = body;
    this.vineTop = vineTop;
    this.walk1 = walk1;
    this.walk2 = walk2;
    this.walk3 = walk3;
    this.peace = peace;
    this.hurt = hurt;
  }

  int getEyes() {
    return eyes;
  }

  int getLegs() {
    return legs;
  }

  int getHands() {
    return hands;
  }

  int getBody() {
    return body;
  }

  int getVineTop() {
    return vineTop;
  }

  int getHurt() {
    return hurt;
  }

  int getPeace() {
    return peace;
  }

  int getWalk1() {
    return walk1;
  }

  int getWalk2() {
    return walk2;
  }

  int getWalk3() {
    return walk3;
  }
}
