package chris_aranha.co.uk.enevo;


import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


// Tests for ArticleActivity
public class ArticleActivityTest {

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<ArticleActivity> activityTestRule =
            new ActivityTestRule<>(ArticleActivity.class);

    // Check if Swipe Container responsible for article listing "Refresh" is available
    @Test
    public void validateArticleNetworkImageView() {
        onView(withId(R.id.article_image)).check(matches(isDisplayed()));
    }
}