package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.matthewsyren.jokedisplayer.JokeDisplayerActivity;

public class MainActivity
        extends AppCompatActivity
        implements IEndpointsAsyncTaskCallback {
    private ProgressBar pbProgressBar;
    @Nullable
    private JokeIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigns View
        pbProgressBar = findViewById(R.id.pb_progress_bar);

        //Initialises the IdlingResource
        getIdlingResource();
    }

    //Initialises the IdlingResource if it is null, and then returns the IdlingResource
    @VisibleForTesting
    public IdlingResource getIdlingResource(){
        if(mIdlingResource == null){
            mIdlingResource = new JokeIdlingResource();
        }
        return mIdlingResource;
    }

    //Calls an AsyncTask to fetch a joke from the backend GCE
    public void tellJoke(View view) {
        //Displays ProgressBar
        pbProgressBar.setVisibility(View.VISIBLE);

        //Sets IdlingResource IdleState to false
        mIdlingResource.setIdleState(false);

        //Calls AsyncTask
        new EndpointsAsyncTask(this).execute();
    }

    //Gets the joke that was retrieved from the backend GCE
    @Override
    public void getJoke(String joke) {
        mIdlingResource.setIdleState(true);

        if(joke != null){
            //Sends the joke to the next Activity
            Intent intent = new Intent(MainActivity.this, JokeDisplayerActivity.class);
            intent.putExtra(JokeDisplayerActivity.JOKE_BUNDLE_KEY, joke);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.error_no_jokes_retrieved), Toast.LENGTH_LONG).show();
        }

        //Hides ProgressBar
        pbProgressBar.setVisibility(View.GONE);
    }
}