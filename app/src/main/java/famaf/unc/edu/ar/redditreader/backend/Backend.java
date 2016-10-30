package famaf.unc.edu.ar.redditreader.backend;

import android.accounts.NetworkErrorException;
import android.content.Context;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import famaf.unc.edu.ar.redditreader.Classes.Listener;
import famaf.unc.edu.ar.redditreader.Classes.Listing;
import famaf.unc.edu.ar.redditreader.Classes.PostModel;

/**
 * Created by mono on 02/10/16.
 */
public class Backend {
    private static Backend backendInstance = new Backend();
    private List<PostModel> mListPostModel;
    private Listener listener = null;

    public static Backend getInstance() {
        return backendInstance;
    }

    private Backend() {
        mListPostModel = new ArrayList<>();
    }

    public void setListener(Listener l) {
        listener = l;
    }

    public List<PostModel> getTopPosts(Context context) {
        try {
            new GetTopPostsTask(context) {
                @Override
                protected void onPostExecute(Listing lst) {
                    listener.nextPosts(lst);
                }
            }.execute(new URL("https://www.reddit.com/top/.json"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return mListPostModel;
    }

}
