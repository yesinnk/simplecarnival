package ca.carnivalgames.simplecarnival.persistence;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
  @PrimaryKey(autoGenerate = true)
  public int id = 0;

  public String username;
  public long dateCreated;

  public User(String username, final long dateCreated) {
    this.username = username;
    this.dateCreated = dateCreated;
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", username='"
        + username
        + '\''
        + ", dateCreated="
        + dateCreated
        + '}';
  }

  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(long dateCreated) {
    this.dateCreated = dateCreated;
  }
}
