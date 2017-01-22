package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.udacity.gradle.builditbigger.Utils.JsonUtils;
import com.udacity.gradle.builditbigger.Utils.NetworkUtils;


public class MainActivity extends AppCompatActivity {

    String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

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

        @Override
        protected void onPostExecute(String movieData) {
            response = movieData;
        }
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
        new FetchMovieTask().execute();
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }


}
