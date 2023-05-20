package com.example.myapplication;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;

@RunWith(AndroidJUnit4.class)
public class RecipeFormActivityTest {

    @Rule
    public ActivityTestRule<RecipeFormActivity> activityRule = new ActivityTestRule<>(RecipeFormActivity.class);

    private RecipeFormActivity activity;

    @Before
    public void setUp() {
        activity = activityRule.getActivity();
    }

    @Test
    public void testRecipeFormSubmission() {
        // Enter recipe title
        String recipeTitle = "Delicious Recipe";
        onView(withId(R.id.title_edit_text)).perform(replaceText(recipeTitle));

        // Enter recipe description
        String recipeDescription = "This is a delicious recipe.";
        onView(withId(R.id.description_edit_text)).perform(replaceText(recipeDescription));

        // Add ingredient
        String ingredientName = "Ingredient 1";
        String ingredientQuantity = "2";
        String ingredientUnit = "cups";
        onView(withId(R.id.add_ingredient_button)).perform(click());
        onView(withId(R.id.ingredients_layout)).check(matches(isDisplayed()));

        // Find the child views within the ingredients_layout ViewGroup
        onView(allOf(withId(R.id.ingredients_layout), withChildAtPosition(0, withText("Enter ingredient name")))).perform(replaceText(ingredientName));
        onView(allOf(withId(R.id.ingredients_layout), withChildAtPosition(2, withText("Enter ingredient quantity")))).perform(replaceText(ingredientQuantity));
        onView(allOf(withId(R.id.ingredients_layout), withChildAtPosition(4, withText("Enter ingredient unit")))).perform(replaceText(ingredientUnit));

        // Submit recipe form
        onView(withId(R.id.submit_button)).perform(click());

        // TODO: Add assertions to check if the recipe is submitted successfully
    }



    private Matcher<View> withChildAtPosition(final int position, final Matcher<View> childMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " matching: ");
                childMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (view.getParent() instanceof ViewGroup) {
                    ViewGroup parent = (ViewGroup) view.getParent();
                    return childMatcher.matches(view) && parent.indexOfChild(view) == position;
                }
                return false;
            }
        };
    }

}
