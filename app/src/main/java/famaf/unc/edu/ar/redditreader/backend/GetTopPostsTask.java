package famaf.unc.edu.ar.redditreader.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import famaf.unc.edu.ar.redditreader.Classes.Listing;
import famaf.unc.edu.ar.redditreader.Classes.PostModel;

/**
 * Created by mono on 22/10/16.
 */

public class GetTopPostsTask extends AsyncTask<URL, Integer, Listing > {

    private final String REDDIT_DOMAIN = "http://www.reddit.com/";

    public GetTopPostsTask() { }

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
        return list != null ? list : null;
    }
}
