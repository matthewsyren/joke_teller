package com.udacity.gradle.builditbigger.utilities;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Used to manage the IdlingResource for the app
 */

public class JokeIdlingResource
        implements IdlingResource {
    @Nullable
    private volatile ResourceCallback mCallback;
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName(){
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow(){
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback){
        mCallback = callback;
    }

    //Sets the IdleState
    public void setIdleState(boolean isIdleNow){
        mIsIdleNow.set(isIdleNow);

        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}