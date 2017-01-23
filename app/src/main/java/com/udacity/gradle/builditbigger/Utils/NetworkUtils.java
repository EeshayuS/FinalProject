package com.udacity.gradle.builditbigger.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String LINK = "https://api.icndb.com/jokes/random";

    public static String buildJokeUrl() {
        URL url;
        String response;
        try {
            url = new URL(LINK);
            response = getResponseFromHttpUrl(url);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
