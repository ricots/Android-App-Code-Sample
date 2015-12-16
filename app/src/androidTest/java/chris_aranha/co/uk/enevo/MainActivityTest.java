package chris_aranha.co.uk.enevo;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

// Tests for MainActivity
public class MainActivityTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    // Check if Swipe Container responsible for article listing "Refresh" is available
    @Test
    public void validateArticlesSwipeContainer() {
        onView(withId(R.id.swipeContainer)).check(matches(isDisplayed()));
    }

    // Check for RecyclerView widget holding articles is available
    @Test
    public void validateArticlesRecyclerView() {
        onView(withId(R.id.cardList)).check(matches(isDisplayed()));
    }
}