package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.NetworkUtils;
import com.example.eeshayu.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.Utils.JsonUtils;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static Toast toast;
    CountDownTimer toastCountDown;
    Loader loader;
    static String jsonJokeResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tellJoke(View view) {
        new EndpointsAsyncTask().execute();
    }


    private void showToast(String joke) {
        int toastDurationInMilliSeconds = joke.length() * 40;

        toast = Toast.makeText(this, joke, Toast.LENGTH_LONG);
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000) {
            public void onTick(long millisUntilFinished) {
                toast.show();
            }

            public void onFinish() {
                toast.cancel();
            }
        };

        toast.show();
        toastCountDown.start();
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new FetchJokeTask(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        String joke = String.valueOf(data).replace("&quot;", "\"");
        showToast(joke);
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }


    static class FetchJokeTask extends AsyncTaskLoader<String> {

        private FetchJokeTask(Context context) {
            super(context);
        }

        @Override
        public String loadInBackground() {

            String joke;

            try {

                joke = JsonUtils.getJokeFromJson(jsonJokeResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;

            }
            return joke;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
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
                return myApiService.myApi().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String result) {
            toast = Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG);
            toast.show();
            // jsonJokeResponse = result;
            // if (toast != null) toast.cancel();
            // if (loader != null) loader.reset();
            // loader = getSupportLoaderManager().initLoader(0, null, MainActivity.this);
            // loader.forceLoad();
        }
    }
}



