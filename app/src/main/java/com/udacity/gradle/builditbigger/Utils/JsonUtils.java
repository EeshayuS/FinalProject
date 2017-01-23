package com.udacity.gradle.builditbigger.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public final class JsonUtils {

    private static final String JSON_JOKE_PATH = "joke";

    public static String getJokeFromJson(String jokeJsonString) throws JSONException {

        String joke;

        JSONObject jokeJson = new JSONObject(jokeJsonString);

        JSONObject joke2 = jokeJson.getJSONObject("value");

        joke = joke2.getString(JSON_JOKE_PATH);

        return joke;

    }

}


