package ca.carnivalgames.simplecarnival;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import ca.carnivalgames.simplecarnival.fragments.menu.Menu;
import ca.carnivalgames.simplecarnival.fragments.menu.MenuItem;
import ca.carnivalgames.simplecarnival.persistence.AppDatabase;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;

/**
 * Hey team, please check the testing documentation
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MinimumExperienceInstrumentedTest {
  private List<MenuItem> menuList;

  @Rule
  public ActivityTestRule<ControllerActivity> mainActivityActivityTestRule =
      new ActivityTestRule<>(ControllerActivity.class);

  @Before
  public void setUp() {
    menuList = Menu.getItemList();
  }

  private void goBack() {
    onView(ViewMatchers.withId(R.id.controller_back_button)).perform(click());
  }

  private void menuClickOn(int index) {
    onView(ViewMatchers.withId(R.id.menu_recycler))
        .perform(RecyclerViewActions.actionOnItemAtPosition(index, click()));
  }

  private void menuClickOnSettings() {
    onView(ViewMatchers.withId(R.id.menu_settings_button)).perform(click());
  }

  private void rotateDevice() {
    mainActivityActivityTestRule
        .getActivity()
        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
    SystemClock.sleep(1000);
    mainActivityActivityTestRule
        .getActivity()
        .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
    SystemClock.sleep(1000);
  }

  @Test
  public void A_logInWithNewUser() {
    // WARNING: All data will be removed
     Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    AppDatabase.getInstance(context).clearAllTables();
    onView(ViewMatchers.withId(R.id.login_on_board_edit_text)).perform(typeText("Test"));
    onView(ViewMatchers.withId(R.id.login_on_board_button)).perform(click());
  }

  @Test
  public void B_InAndOutOfMenu() {
    for (int i = 0; i < menuList.size(); i++) {
      menuClickOn(i);
      goBack();
    }
    menuClickOnSettings();
    goBack();
  }

  @Test
  public void B_rotateEveryViewFromMenu() {
    rotateDevice();
    for (int i = 0; i < menuList.size(); i++) {
      menuClickOn(i);
      rotateDevice();
      goBack();
    }
    menuClickOnSettings();
    rotateDevice();
    goBack();
  }
}
