package com.example.alizeecamarasa.planit.events;

import android.util.Pair;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alizeecamarasa on 30/12/14.
 */
public class SimpleRestApi {

    public static Pair<Integer, String> post (String apiUrl, String jsonMessage)  throws IOException {
        InputStream is = null;

        try {

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/json");
            conn.setRequestProperty("Content-Length", "" +
                    Integer.toString(jsonMessage.getBytes().length));
            conn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream (
                    conn.getOutputStream ());
            wr.writeBytes (jsonMessage);
            wr.flush ();
            wr.close ();

            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = getStringFromStream(is);
            return Pair.create(response, contentAsString);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static Pair<Integer, String> get(String apiUrl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = getStringFromStream(is);
            return Pair.create(response, contentAsString);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    static String getStringFromStream(InputStream stream) throws IOException, UnsupportedEncodingException {
        Reader reader = new InputStreamReader(stream, "UTF-8");

        char[] buffer = new char[2*1024];

        StringBuilder builder= new StringBuilder();

        int read = 0;
        while((read = reader.read(buffer))>0){
            builder.append(buffer);
        }

        return builder.toString();
    }
}
