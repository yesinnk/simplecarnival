package ca.carnivalgames.simplecarnival.fragments.menu;

public class MenuItem {

  private final String title;
  private final int resourceId;
  private final Class itemClass;

  MenuItem(String title, int resourceId, Class itemClass) {
    this.title = title;
    this.resourceId = resourceId;
    this.itemClass = itemClass;
  }

  public String getTitle() {
    return title;
  }

  int getResourceId() {
    return resourceId;
  }

  Class getItemClass() {
    return itemClass;
  }
}
