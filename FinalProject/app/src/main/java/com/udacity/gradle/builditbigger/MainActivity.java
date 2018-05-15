package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matthewsyren.jokedisplayer.JokeDisplayerActivity;
import com.udacity.gradle.builditbigger.utilities.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.utilities.IEndpointsAsyncTaskCallback;
import com.udacity.gradle.builditbigger.utilities.JokeIdlingResource;
import com.udacity.gradle.builditbigger.utilities.NetworkUtilities;

public class MainActivity
        extends AppCompatActivity
        implements IEndpointsAsyncTaskCallback {
    private ProgressBar pbProgressBar;
    private Button btnTellJoke;
    private TextView tvInstructions;

    @Nullable
    private JokeIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assigns Views
        pbProgressBar = findViewById(R.id.pb_progress_bar);
        btnTellJoke = findViewById(R.id.btn_tell_joke);
        tvInstructions = findViewById(R.id.instructions_text_view);

        //Initialises the IdlingResource
        getIdlingResource();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Displays other Views if ProgressBar is not visible
        if(pbProgressBar.getVisibility() != View.VISIBLE){
            tvInstructions.setVisibility(View.VISIBLE);
            btnTellJoke.setVisibility(View.VISIBLE);
        }
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
        //Calls the AsyncTask to fetch a joke if there is an Internet connection
        if(NetworkUtilities.isOnline(this)){
            //Displays the ProgressBar and hides the Button and the instructions
            pbProgressBar.setVisibility(View.VISIBLE);
            tvInstructions.setVisibility(View.GONE);
            btnTellJoke.setVisibility(View.GONE);

            //Sets the IdlingResource's IdleState to false
            mIdlingResource.setIdleState(false);

            //Requests a joke
            new EndpointsAsyncTask(this).execute();
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.error_no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    //Sends the joke that was retrieved from the backend GCE to the Android library to be displayed
    @Override
    public void sendJoke(String joke) {
        //Sets the IdlingResource's IdleState to true
        mIdlingResource.setIdleState(true);

        if(joke != null && joke.length() > 0){
            //Sends the joke to the next Activity
            Intent intent = new Intent(MainActivity.this, JokeDisplayerActivity.class);
            intent.putExtra(JokeDisplayerActivity.JOKE_BUNDLE_KEY, joke);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), getString(R.string.error_no_jokes_retrieved), Toast.LENGTH_LONG).show();
        }

        //Hides the ProgressBar
        pbProgressBar.setVisibility(View.GONE);
    }
}