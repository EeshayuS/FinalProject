package com.udacity.gradle.builditbigger.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

    private static final String JSON_JOKE_PATH = "joke";

    public static String getJokeFromJson(String jokeJsonString)
            throws JSONException {

        String joke;

        JSONObject jokeJson = new JSONObject(jokeJsonString);

        joke = jokeJson.getString(JSON_JOKE_PATH);

        return joke;

    }

}


