package ca.carnivalgames.simplecarnival.persistence;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import static ca.carnivalgames.simplecarnival.persistence.AppInfo.NOT_LOGGED_IN;

// May use dependency injection in the future
public class UserState {
  private static List<User> userList = new ArrayList<>();
  private static int currentUserID = NOT_LOGGED_IN;

  public static List<User> getUserList() {
    return userList;
  }

  public static void setInfo(@NonNull List<User> userList, int currentUserID) {
    UserState.userList = userList;
    UserState.currentUserID = currentUserID;
  }

  public static int getCurrentUserID() {
    return currentUserID;
  }

  public static Boolean isFirstLogIn() {
    return userList.size() == 0;
  }

  public static Boolean hasUsers() {
    return userList.size() > 0;
  }

  public static User getLoggedIn() {
    if (userList.size() == 1) return userList.get(0);
    return null;
  }
}
