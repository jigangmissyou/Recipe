package com.example.myapplication;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.Root;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.matcher.ViewMatchers.Visibility;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RecipeFormActivityTest {

    @Rule
    public ActivityScenarioRule<RecipeFormActivity> activityRule =
            new ActivityScenarioRule<>(RecipeFormActivity.class);

    @Test
    public void testAddIngredient() {
        // Find and perform actions on views using Espresso
        Espresso.onView(ViewMatchers.withId(R.id.add_ingredient_button))
                .perform(ViewActions.click());

        // Check if the ingredient EditText is displayed
        Espresso.onView(ViewMatchers.withHint("Enter ingredient name"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testAddStep() {
        // Find and perform actions on views using Espresso
        Espresso.onView(ViewMatchers.withId(R.id.add_step_button))
                .perform(ViewActions.click());

        // Check if the step EditText is displayed
        Espresso.onView(ViewMatchers.withId(R.id.step_edit_text))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    // Add more test cases for other scenarios

}
