package com.example;

import java.util.ArrayList;
import java.util.Random;

public class jokes {

    private static ArrayList<String> joke = new ArrayList<>();

    public static String getJoke() {
        Random random = new Random();
        joke.add("NOT EVEN CLOSE BABY");
        joke.add("NOW FOR MY SPECIAL MOVE");
        joke.add("SUPA MARIO BRUDDAS 2 BABY");
        joke.add("KNACK ladies and gentlemen, KNACK");
        return joke.get(random.nextInt(joke.size()));
    }
}
