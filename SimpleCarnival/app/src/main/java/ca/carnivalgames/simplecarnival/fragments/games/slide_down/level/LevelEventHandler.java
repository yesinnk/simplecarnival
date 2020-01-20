package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level;

/**
 * A LevelEventHandler observers events from a Level Event observer
 * */
public interface LevelEventHandler {
  /** The event called when a level is completed*/
  void onLevelComplete();

  /** The event called when another level need to be loaded */
  void onNextLevel();

  /** The event called when the game has no more levels*/
  void onGameComplete();

  /** The event called when a Level Object has been interacted with*/
  void onInteraction(InteractionEvent interactionEvent);

  /** The event called when a level is Loaded*/
  void onLoadLevel();
}
