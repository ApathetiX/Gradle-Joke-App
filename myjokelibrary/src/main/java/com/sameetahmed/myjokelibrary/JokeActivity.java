package com.sameetahmed.myjokelibrary;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class JokeActivity extends AppCompatActivity {
    public static final String JOKE_KEY = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = null;
        if (getIntent() != null) {
            joke = getIntent().getExtras().getString(JOKE_KEY);
        }

        Bundle args = new Bundle();
        args.putString(JOKE_KEY, joke);

        JokeFragment jokeFragment = new JokeFragment();
        jokeFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.joke_text_container, jokeFragment)
                .commit();
    }
}
