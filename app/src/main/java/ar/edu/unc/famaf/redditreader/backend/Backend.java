package ar.edu.unc.famaf.redditreader.backend;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.classes.Listing;
import ar.edu.unc.famaf.redditreader.classes.PostModel;
import ar.edu.unc.famaf.redditreader.classes.PostsIteratorListener;

/**
 * Created by mono on 02/10/16.
 */
public class Backend {
    private static Backend backendInstance = new Backend();

    private int mPostIndex = 0;
    private int mTabIndex = 0;
    private String[] mPostAfter = null;

    public static Backend getInstance() {
        return backendInstance;
    }

    private Backend() {
    }

    public void getNextPosts(final PostsIteratorListener listener, final Context context) {
        if (mPostAfter == null) initMPostAfter(context);

        RedditDB rdb = new RedditDB();
        List<PostModel> list = rdb.getPostsAfterIndex(context, mPostIndex, mTabIndex);
        if (list.size() == 0) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null && ni.isConnected()) {
                String[] subreddits = context.getResources().getStringArray(R.array.subreddit_uris);
                try {
                    new GetTopPostsTask(context) {
                        @Override
                        protected void onPostExecute(Listing listing) {
                            RedditDB rdb = new RedditDB();
                            if (listing != null) {
                                rdb.insert(context, listing.getListPostModel(), mTabIndex);
                                mPostAfter[mTabIndex] = listing.getAfter();
                                List<PostModel> list = rdb.getPostsAfterIndex(context, mPostIndex, mTabIndex);
                                mPostIndex += list.size();
                                listener.nextPosts(list);
                            } else {
                                // Error downloading posts
                            }
                        }
                    }.execute(new URL("https://www.reddit.com/"
                            + subreddits[mTabIndex]
                            + "/.json?limit=50&after=" + mPostAfter[mTabIndex]));

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
            mPostIndex += list.size();
            listener.nextPosts(list);
        }
    }

    public void setSubredditIndex(int subredditIndex, Context context) {
        mTabIndex = subredditIndex;
        mPostIndex = 0;
        /*
        RedditDB rdb = new RedditDB();
        rdb.cleanDatabase(context);
        */
    }

    private void initMPostAfter(Context context) {
        String[] subreddits = context.getResources().getStringArray(R.array.subreddit_uris);
        mPostAfter = new String[subreddits.length];
        for (int i = 0; i < subreddits.length; i++) {
            mPostAfter[i] = "";
        }
    }
}
