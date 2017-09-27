package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.MyJokes;
import com.sameetahmed.myjokelibrary.JokeActivity;


public class MainActivity extends AppCompatActivity {
    private MyJokes mJokes;
    public static final String JOKE_KEY = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiates the java jokes library
        mJokes = new MyJokes();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        // Tells a joke from the java lib onCLick
        Toast.makeText(this, mJokes.getJoke(), Toast.LENGTH_SHORT).show();

        // Sends an intent to the JokeActivity
        Intent jokeIntent = new Intent(this, JokeActivity.class);
        jokeIntent.putExtra(JOKE_KEY, mJokes.getJoke());
        startActivity(jokeIntent);
    }


}
