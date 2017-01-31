package com.udacity.gradle.builditbigger;

import org.junit.Test;

import com.example.jokes;

public class EndpointsAsyncTaskTest {
    @Test
    public void verifyJoke() {
        assert jokes.getJoke().contains(MainActivity.joke);
    }
}