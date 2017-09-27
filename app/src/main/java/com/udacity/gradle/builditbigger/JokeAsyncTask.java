package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.sameetahmed.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.sameetahmed.myjokelibrary.JokeActivity;

import java.io.IOException;



public class JokeAsyncTask extends AsyncTask<Void, Void, String> {
    private MyApi myApiService = null;
    private MainActivity.JsonGetTaskListener mListener = null;
    private Context mContext;
    private Exception mError;
    public static final String JOKE_KEY = "joke";

    public JokeAsyncTask(Context context) {
        mContext = context;
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
        Intent jokeIntent = new Intent(mContext, JokeActivity.class);
        jokeIntent.putExtra(JOKE_KEY, result);
        mContext.startActivity(jokeIntent);
    }

    public JokeAsyncTask setListener(MainActivity.JsonGetTaskListener listener) {
        this.mListener = listener;
        return this;
    }
}
