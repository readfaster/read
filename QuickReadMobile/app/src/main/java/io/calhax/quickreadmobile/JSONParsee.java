package io.calhax.quickreadmobile;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by james on 10/4/14.
 */
public class JSONParsee extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... resource) {
        String data = null;
        try {
            URL url = new URL(resource[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            data = convertStreamToString(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private String convertStreamToString(InputStream in) {
        Scanner s = new Scanner(in);
        return s.useDelimiter("\\A").hasNext() ? s.next() : "";
    }
}

