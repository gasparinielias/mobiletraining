package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.Classes.Listener;
import ar.edu.unc.famaf.redditreader.Classes.Listing;
import ar.edu.unc.famaf.redditreader.Classes.PostModel;

/**
 * Created by mono on 02/10/16.
 */
public class Backend {
    private static Backend backendInstance = new Backend();
    private List<PostModel> mListPostModel;

    public static Backend getInstance() {
        return backendInstance;
    }

    private Backend() {
        mListPostModel = new ArrayList<>();
    }

    public List<PostModel> getTopPosts(final Context context, final Listener listener) {
        try {
            new GetTopPostsTask(context) {
                @Override
                protected void onPostExecute(Listing listing) {
                    RedditDB rdb = new RedditDB();
                    if (listing != null) {
                        rdb.cleanDatabase(context);
                        rdb.insert(context, listing.getListPostModel());
                    } else {
                        listing = rdb.getAllPosts(context);
                    }
                    listener.nextPosts(listing);
                }
            }.execute(new URL("https://www.reddit.com/top/.json?limit=50"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mListPostModel;
    }

}
