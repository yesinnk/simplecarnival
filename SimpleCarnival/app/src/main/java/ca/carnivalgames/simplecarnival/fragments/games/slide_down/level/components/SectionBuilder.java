package ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components;

import android.content.res.Resources;
import android.graphics.Bitmap;

import java.util.ArrayList;

import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItem;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItemFactory;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.LevelItemMap;
import ca.carnivalgames.simplecarnival.fragments.games.slide_down.level.components.items.types.LevelItemType;

import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.generateClippedImage;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.generateImage;
import static ca.carnivalgames.simplecarnival.fragments.games.slide_down.SlideUtils.generateInversedClippedImage;

/**
 * SectionBuilder is used as a tool for constructing levels in a easy manor. It can repeat
 * background and help paint LevelObjects. It inherits from the ArrayList as segments are essential
 * just a list of data that hold background data.
 */
public class SectionBuilder extends ArrayList<LevelSegment> {
  //  private LevelDesigner levelSegments;
  private Resources resources;
  private int repeatAmount;
  private int vine;
  private int currentBackgroundResourceID;
  private Bitmap background;
  private Bitmap lastBackground;
  private Bitmap clippedBackground;
  private ArrayList<LevelItemPainter> objectPainters;
  private final int OBJECT_ROWS = 4;
  private int levelWorstTime;

  /** Method used to reinitialize the section builder */
  private void initialize() {
    clippedBackground = null;
    background = null;
    vine = 0;
    repeatAmount = 0;
    objectPainters = null;
  }

  protected SectionBuilder(Resources resources) {
    initialize();
    this.resources = resources;
  }

  protected SectionBuilder addVine(int vine) {
    this.vine = vine;
    return this;
  }

  /**
   * Add objects to each level segment such as enemies and coins. Section Objects should not exceed
   * 12 combined and no single object should exceed 6.
   *
   * <p>// * @param object The object type that will be added to section.
   *
   * @return This Section Builder
   */
  public SectionBuilder addLevelItem(LevelItemType objectType, int priority) {
    if (objectPainters == null) {
      objectPainters = new ArrayList<>();
    }
    objectPainters.add(new LevelItemPainter(objectType, priority));
    return this;
  }

  /**
   * Adds a background to the current segment painter
   *
   * @param resourceID The id of the background to paint
   * @return This Section Builder
   */
  public SectionBuilder addBackground(int resourceID) {
    background = generateImage(resources, resourceID);
    clippedBackground = generateClippedImage(resources, resourceID);
    currentBackgroundResourceID = resourceID;
    return this;
  }

  /**
   * The amount of time the segment should repeat
   *
   * @param amount The amount of times this segment should repeat
   * @return This Section Builder
   */
  public SectionBuilder repeatBackground(int amount) {
    repeatAmount = amount;
    return this;
  }

  /**
   * Private Helper that generates a SegmentObject Map from a segment object painter
   *
   * @param index the current index of the segment in the List of segments currently being drawn on
   *     the screen
   * @return a new LevelItemMap
   */
  private LevelItemMap getSegmentObjectMap(int index) {
    if (objectPainters == null) return null;

    LevelItemMap levelItemMap = new LevelItemMap(OBJECT_ROWS);
    int rowOffset = OBJECT_ROWS * index;

    for (int row = 0; row < OBJECT_ROWS; row++)
      for (LevelItemPainter objectPainter : objectPainters) {
        if (objectPainter.shouldPaintRow(rowOffset + row)) {
          LevelItem levelItem = LevelItemFactory.createLevelItem(objectPainter.getObjectType());
          levelItemMap.put(objectPainter.getRandomSide(), row, levelItem);
        }
      }
    return levelItemMap;
  }

  /** Adds all the SegmentBuilder information using the ArrayList helper methods */
  public void addToLevel() {

    for (int i = 0; i < repeatAmount + 1; i++) {
      LevelItemMap levelItemMap = getSegmentObjectMap(i);

      if (i == 0) add(new LevelSegment(vine, clippedBackground, lastBackground, levelItemMap));
      else add(new LevelSegment(vine, background, null, levelItemMap));
    }

    if (background == null) lastBackground = null;
    else generateLastBackground();

    initialize();
  }

  /** A function that generates a clipped background that makes background look seamless */
  private void generateLastBackground() {
    lastBackground = generateInversedClippedImage(resources, currentBackgroundResourceID);
  }

  //  void doNotMergeLastBackground() {
  //    lastBackground = null;
  //  }

  /**
   * This method is used to calculate a users score.
   *
   * @return The longest time it will take a user to complete the level
   */
  public int getLevelWorstTime() {
    return levelWorstTime;
  }

  /**
   * This method is used to calculate a users score by differing their times to the worst times.
   *
   * @param levelWorstTime The longest time it will take a user to complete the level measured in
   *     seconds
   */
  public void setLevelWorstTime(int levelWorstTime) {
    this.levelWorstTime = levelWorstTime;
  }
}
