package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        //Gets the IdlingResource
        mIdlingResource = mActivityRule.getActivity()
                .getIdlingResource();

        //Registers the IdlingResource
        IdlingRegistry.getInstance()
                .register(mIdlingResource);
    }

    @Test
    public void clickButton_JokeDisplayed(){
        //Clicks on button
        onView(withId(R.id.btn_tell_joke))
                .perform(click());

        //Checks that the TextView is not empty
        onView(withId(R.id.tv_joke))
                .check(matches(not(withText(""))));
    }

    @After
    public void unregisterIdlingResource(){
        //Unregisters the IdlingResource
        if(mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}