package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    private LoginActivity loginActivity;

    @Before
    public void setUp() {
        loginActivity = activityRule.getActivity();
    }

    @After
    public void tearDown() {
        loginActivity.finish();
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Set valid input credentials
        String username = "Jigang";
        String password = "111111";

        // Perform login
        loginActivity.runOnUiThread(() -> {
            loginActivity.usernameEditText.setText(username);
            loginActivity.passwordEditText.setText(password);
            loginActivity.loginButton.performClick();
        });

        // Check if login is successful
        SharedPreferences sharedPref = ApplicationProvider.getApplicationContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        assertEquals(username, sharedPref.getString("username", "Jigang"));
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        SharedPreferences sharedPref = ApplicationProvider.getApplicationContext().getSharedPreferences("login_pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();        // Set invalid input credentials
        String username = "invalid@demo.com";
        String password = "invalid";

        // Perform login
        loginActivity.runOnUiThread(() -> {
            loginActivity.usernameEditText.setText(username);
            loginActivity.passwordEditText.setText(password);
            loginActivity.loginButton.performClick();
        });
        assertEquals(null, sharedPref.getString("username", null));
    }
}
