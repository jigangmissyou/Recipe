package com.example.myapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.myapplication.HomeActivity;
import com.example.myapplication.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class HomeActivityTest {

    @Rule
    public ActivityScenarioRule<HomeActivity> activityRule =
            new ActivityScenarioRule<>(HomeActivity.class);

    @Test
    public void testGalleryScrolling() {
        // scroll to the third item in the horizontal gallery
        Espresso.onView(ViewMatchers.withId(R.id.galleryRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(2));
    }

    @Test
    public void testGalleryClick() {
        // click on the first item in the horizontal gallery
        Espresso.onView(ViewMatchers.withId(R.id.galleryRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }

    @Test
    public void testContentListScrolling() {
        // scroll to the third item in the vertical content list
        Espresso.onView(ViewMatchers.withId(R.id.content_item_layout))
                .perform(RecyclerViewActions.scrollToPosition(2));

    }

    @Test
    public void testContentListClick() {
        // click on the first item in the vertical content list
        Espresso.onView(ViewMatchers.withId(R.id.content_item_layout))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
    }

    @Test
    public void testBottomNavigationToSecond() {
        // click on the third menu item in the bottom navigation bar
        Espresso.onView(ViewMatchers.withId(R.id.menu_item_2)).perform(ViewActions.click());
    }

    @Test
    public void testBottomNavigationBack() {
        // click on the back button in the bottom navigation bar
        Espresso.onView(ViewMatchers.withId(R.id.menu_item_1)).perform(ViewActions.click());
    }
}
