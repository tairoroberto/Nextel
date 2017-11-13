package com.tairoroberto.nextel.home.view;

import android.os.Build;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.tairoroberto.nextel.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import timber.log.Timber;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by tairo on 11/12/17 10:39 PM.
 */

@RunWith(AndroidJUnit4.class)
public class DetailTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void scrollToPositionAndVerifyAlertTest() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction recyclerView = onView(allOf(withId(R.id.recyclerView), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(5, click()));

        allowPermissionsIfNeeded();

        ViewInteraction title = onView(allOf(withId(R.id.textViewName), withText("Spider-Man: Homecoming")));
        title.check(matches(isDisplayed()));

        allowPermissionsIfNeeded();
    }

    private static void allowPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT >= 23) {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = device.findObject(new UiSelector().text("ALLOW"));
            if (allowPermissions.exists()) {
                try {
                    allowPermissions.click();
                } catch (UiObjectNotFoundException e) {
                    Timber.e(e, "There is no permissions dialog to interact with ");
                }
            }
        }
    }
}
