package ar.edu.unc.famaf.redditreader.backend;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.classes.Listing;
import ar.edu.unc.famaf.redditreader.classes.PostModel;
import ar.edu.unc.famaf.redditreader.classes.PostsIteratorListener;

/**
 * Created by mono on 02/10/16.
 */
public class Backend {
    private static Backend backendInstance = new Backend();
    private int postIndex = 0;
    private String postAfter = "";

    public static Backend getInstance() {
        return backendInstance;
    }

    private Backend() { }

    public void getNextPosts(final PostsIteratorListener listener, final Context context) {
        RedditDB rdb = new RedditDB();
        List<PostModel> list = rdb.getPostsAfterIndex(context, postIndex);
        if (list.size() == 0) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null && ni.isConnected()) {
                try {
                    new GetTopPostsTask(context) {
                        @Override
                        protected void onPostExecute(Listing listing) {
                            RedditDB rdb = new RedditDB();
                            if (listing != null) {
                                rdb.insert(context, listing.getListPostModel());
                                postAfter = listing.getAfter();
                                List<PostModel> list = rdb.getPostsAfterIndex(context, postIndex);
                                postIndex += list.size();
                                listener.nextPosts(list);
                            } else {
                                // Error downloading posts
                            }
                        }
                    }.execute(new URL("https://www.reddit.com/top/.json?limit=50&after=" + postAfter));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // No internet connection nor posts to show, display error
                    }
                });
            }
        } else {
            postIndex += list.size();
            listener.nextPosts(list);
        }
    }

}
