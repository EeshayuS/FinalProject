package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.Utils.JsonUtils;
import com.udacity.gradle.builditbigger.Utils.NetworkUtils;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{

    private static Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new FetchJokeTask(this);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        toast = Toast.makeText(MainActivity.this, String.valueOf(data), Toast.LENGTH_LONG);
        toast.show();
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

            String jsonJokeResponse = NetworkUtils.buildJokeUrl();

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

    public void tellJoke(View view) {
        if (toast != null) toast.cancel();
        Loader loader = getSupportLoaderManager().initLoader(0, null, this);
        loader.forceLoad();
    }

}
