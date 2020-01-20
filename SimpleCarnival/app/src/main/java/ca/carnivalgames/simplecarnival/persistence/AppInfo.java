package ca.carnivalgames.simplecarnival.persistence;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AppInfo {
  public static final int NOT_LOGGED_IN = -1;

  @PrimaryKey private int id;
  public long dateCreated;
  public int currentUserID;

  public AppInfo(long dateCreated, int currentUserID) {
    this.id = 0;
    this.dateCreated = dateCreated;
    this.currentUserID = currentUserID;
  }

  @NonNull
  @Override
  public String toString() {
    return "AppInfo{"
        + "id="
        + id
        + ", dateCreated="
        + dateCreated
        + ", currentUserID="
        + currentUserID
        + '}';
  }

  public int getId() {
    return id;
  }

  public long getDateCreated() {
    return dateCreated;
  }

  public int getCurrentUserID() {
    return currentUserID;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDateCreated(Long dateCreated) {
    this.dateCreated = dateCreated;
  }

  public void setCurrentUserID(int currentUserID) {
    this.currentUserID = currentUserID;
  }
}
