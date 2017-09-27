package com.udacity.gradle.builditbigger.test;

import android.test.InstrumentationTestCase;

import com.udacity.gradle.builditbigger.JokeAsyncTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;



public class AsyncTest extends InstrumentationTestCase {

    final CountDownLatch signal = new CountDownLatch(1);


    public void testAsync() throws Throwable {

        final JokeAsyncTask jokeAsyncTask = new JokeAsyncTask() {

            @Override
            protected String doInBackground(Void... params) {
                return super.doInBackground(params);
            }

            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
                signal.countDown();
            }
        };

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                jokeAsyncTask.execute();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }
}
