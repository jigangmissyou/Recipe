package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.widget.Button;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> activityRule = new ActivityTestRule<>(RegisterActivity.class);

    private RegisterActivity registerActivity;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;

    @Before
    public void setUp() {
        registerActivity = activityRule.getActivity();
        usernameEditText = registerActivity.findViewById(R.id.usernameEditText);
        emailEditText = registerActivity.findViewById(R.id.emailEditText);
        passwordEditText = registerActivity.findViewById(R.id.passwordEditText);
        registerButton = registerActivity.findViewById(R.id.registerButton);
    }

    @After
    public void tearDown() {
        registerActivity.finish();
    }

    @Test
    public void testRegistrationWithValidData() throws Throwable {
        // Set valid input data
        usernameEditText.setText("john");
        emailEditText.setText("john@example.com");
        passwordEditText.setText("password");
        String confirmPassword = "password";
        activityRule.runOnUiThread(() -> {
            // Perform registration
            registerButton.performClick();

            // Check if registration is successful
            assertTrue(registerActivity.isRegistrationSuccessful());
        });

    }

    @Test
    public void testRegistrationWithInvalidUsername() throws Throwable {
        // Set invalid username
        usernameEditText.setText("j");
        emailEditText.setText("john@example.com");
        passwordEditText.setText("password");
        String confirmPassword = "password";
        activityRule.runOnUiThread(() -> {
            // Perform registration
            registerButton.performClick();

            // Check if registration fails and appropriate error is displayed
            assertFalse(registerActivity.isRegistrationSuccessful());
            assertEquals("Username must between 3 and 20 characters long!", usernameEditText.getError().toString());
        });
    }

    @Test
    public void testRegistrationWithInvalidEmail() throws Throwable {
        // Set invalid email
        usernameEditText.setText("john");
        emailEditText.setText("john");
        passwordEditText.setText("password");
        String confirmPassword = "password";

        // Perform registration
        activityRule.runOnUiThread(() -> {
            registerButton.performClick();

            // Check if registration fails and appropriate error is displayed
            assertFalse(registerActivity.isRegistrationSuccessful());
            assertEquals("Please enter a valid email address!", emailEditText.getError().toString());
        });

    }

    @Test
    public void testRegistrationWithInvalidPassword() throws Throwable {
        // Set invalid password
        usernameEditText.setText("john");
        emailEditText.setText("john@example.com");
        passwordEditText.setText("pass");
        String confirmPassword = "pass";

        // Perform registration
        activityRule.runOnUiThread(() -> {
            registerButton.performClick();

            // Check if registration fails and appropriate error is displayed
            assertFalse(registerActivity.isRegistrationSuccessful());
            assertEquals("Password must be at least 6 characters long!", passwordEditText.getError().toString());
        });
    }
}
