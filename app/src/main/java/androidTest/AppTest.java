package androidTest;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.MainActivity;

import java.util.concurrent.CountDownLatch;



public class AppTest extends ApplicationTestCase{
    String mJsonString = null;
    Exception mError = null;
    CountDownLatch signal = null;

    public AppTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testAlbumGetTask() throws InterruptedException {

        MainActivity.EndpointsAsyncTask task = new MainActivity().new EndpointsAsyncTask(getContext());
        task.setListener(new MainActivity.JsonGetTaskListener() {
            @Override
            public void onComplete(String jsonString, Exception e) {
                mJsonString = jsonString;
                mError = e;
                signal.countDown();
            }
        }).execute();

        signal.await();

        assertNull(mError);
        assertFalse(TextUtils.isEmpty(mJsonString));
    }
}
