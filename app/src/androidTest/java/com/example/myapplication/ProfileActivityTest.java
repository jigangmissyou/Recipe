package com.example.myapplication;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;
import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<ProfileActivity> activityRule = new ActivityTestRule<>(ProfileActivity.class);

    @Test
    public void test1_ProfileDisplayed() {
        // Check if profile name and bio are displayed
        String longText = "This is a long text for profile name";
        ProfileActivity profileActivity = activityRule.getActivity();
        profileActivity.runOnUiThread(() -> {
            TextView profileName = profileActivity.findViewById(R.id.profile_name);
            profileName.setText(longText);
        });
        SystemClock.sleep(500);
        Espresso.onView(withId(R.id.profile_name))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.profile_bio))
                .check(matches(isDisplayed()));
    }

    @Test
    public void test2_LogoutButton() {
        // Perform click on logout button
        Espresso.onView(withId(R.id.logout_button))
                .perform(click());

        // Check if MainActivity is displayed after logout
        Espresso.onView(withId(R.id.title_text_view))
                .check(matches(isDisplayed()));
    }
}

