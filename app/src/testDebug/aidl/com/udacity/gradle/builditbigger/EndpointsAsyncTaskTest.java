package com.udacity.gradle.builditbigger;

import org.junit.Test;
import org.junit.experimental.max.MaxCore;

import com.example.jokes;

import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class EndpointsAsyncTaskTest {
    @Test
    public void verifyJoke() {

        try {
            OnTaskCompleted onTaskCompleted = new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(String result) {

                }
            };
            MainActivity.EndpointsAsyncTask jokeTask = new MainActivity.EndpointsAsyncTask(onTaskCompleted);
            jokeTask.execute();
            String joke = jokeTask.get(30, TimeUnit.SECONDS);
            assert (jokes.getAllJokes().contains(joke));
        } catch (Exception e){
            fail("Timed out");
        }
    }
}