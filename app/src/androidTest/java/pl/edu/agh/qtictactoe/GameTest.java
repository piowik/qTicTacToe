package pl.edu.agh.qtictactoe;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class GameTest {

    @Rule
    public final ActivityTestRule<GameActivity> main = new ActivityTestRule<>(GameActivity.class);

    @Test
    public void shouldNotDisplayWaitingTextWhenInSingleplayerGame() {
        onView(withId(R.id.waitingText)).check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldNotAcceptLoopSolutionWhenInvalid() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click())); // invalid solution
        onView(withText(R.string.wrong_loop_solution)).
                inRoot(withDecorView(not(is(main.getActivity().getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayDrawWhenDraw() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        onView(withText("DRAW")).check(matches(isDisplayed()));
    }


    @Test
    public void shouldDisplayXWinsWhenXWins() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6, click()));

        onView(withText("X WIN GAME")).check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayOWinsWhenOWins() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(4, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(7, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(8, click()));

        onView(withText("O WIN GAME")).check(matches(isDisplayed()));
    }
}
