package pl.edu.agh.qtictactoe;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public final ActivityTestRule<MainActivity> main = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void shouldOpenGameActivityWhenClickingSingleplayer() {
        onView(withId(R.id.singleplayer_button)).perform(click());
        intended(hasComponent(GameActivity.class.getName()));
    }

    @Test
    public void shouldNotCrashWhenNavigating() {
        onView(withId(R.id.multiplayer_button)).perform(click());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.multiplayer_button)).perform(click());
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}
