package ca.carnivalgames.simplecarnival.fragments.login;

import ca.carnivalgames.simplecarnival.persistence.User;

interface LoginItemClickDelegate {
  void onMenuItemClick(User user, int index);
}
