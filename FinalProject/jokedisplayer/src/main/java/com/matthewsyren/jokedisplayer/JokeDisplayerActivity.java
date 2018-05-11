package com.matthewsyren.jokedisplayer;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class JokeDisplayerActivity
        extends AppCompatActivity {
    public static final String JOKE_BUNDLE_KEY = "joke_bundle_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_displayer);

        //Fetches joke from the Bundle and displays it
        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey(JOKE_BUNDLE_KEY)){
            //Displays the joke in a TextView
            String joke = bundle.getString(JOKE_BUNDLE_KEY);
            TextView tvJoke = findViewById(R.id.tv_joke);
            tvJoke.setText(joke);
        }

        //Displays back button in the ActionBar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}