package famaf.unc.edu.ar.redditreader.backend;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import famaf.unc.edu.ar.redditreader.PostModel;

/**
 * Created by mono on 22/10/16.
 */

public class GetTopPostsTask {
    private static GetTopPostsTask instance = new GetTopPostsTask();
    private List<PostModel> mPostModel = null;
    private final String REDDIT_DOMAIN = "http://www.reddit.com/";

    public GetTopPostsTask getInstance() {
        return instance;
    }

    public List<PostModel> getTopPosts() throws MalformedURLException {
        String subreddit = "";
        String listing = "/top.json";
        // Check network info
        // getPostsAsJsonTask
        URL url = new URL(REDDIT_DOMAIN + subreddit + listing);
        return mPostModel;
    }

    protected class getPostsAsJsonTask extends AsyncTask<URL, Integer, List<PostModel> > {
        @Override
        protected List<PostModel> doInBackground(URL... params) {
            List<PostModel> mListPostModel = null;
            try {
                HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
                conn.setRequestMethod("GET");
                mListPostModel = new Parser(conn.getInputStream()).readJsonStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mListPostModel;
        }

    }

}
