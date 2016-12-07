package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.classes.Listing;

/**
 * Created by mono on 22/10/16.
 */

public class GetTopPostsTask extends AsyncTask<URL, Integer, Listing > {

    private Context context;

    public GetTopPostsTask(Context ctx) { context = ctx; }

    @Override
    protected Listing doInBackground(URL... params) {
        Listing list = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
            conn.setRequestMethod("GET");
            list = new Parser(conn.getInputStream()).readJsonStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
