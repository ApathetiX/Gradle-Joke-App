package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.MyJokes;
import com.example.sameetahmed.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.sameetahmed.myjokelibrary.JokeActivity;

import java.io.IOException;


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

        new EndpointsAsyncTask(this).execute();
    }

    public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;
        private JsonGetTaskListener mListener = null;
        private Context context;
        private Exception mError;

        public EndpointsAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(Void... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            try {
                return myApiService.getJoke("Sameet").execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }



        @Override
        protected void onPostExecute(String result) {
            if (this.mListener != null)
                this.mListener.onComplete(result, mError);
            // Sends an intent to the JokeActivity
            Intent jokeIntent = new Intent(getApplicationContext(), JokeActivity.class);
            jokeIntent.putExtra(JOKE_KEY, result);
            context.startActivity(jokeIntent);
        }

        public EndpointsAsyncTask setListener(JsonGetTaskListener listener) {
            this.mListener = listener;
            return this;
        }
    }

    public interface JsonGetTaskListener {
        void onComplete(String jsonString, Exception e);
    }


}
