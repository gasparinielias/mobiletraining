package famaf.unc.edu.ar.redditreader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        PostModel p1 = new PostModel("title1", "content1", "subreddit1", 50, "01/01/1999", "https://estereogramas.files.wordpress.com/2015/07/001.jpg");
        PostModel p2 = new PostModel("title2", "content2", "subreddit2", 1, "02/01/1999", "https://estereogramas.files.wordpress.com/2015/07/001.jpg");
        PostModel p3 = new PostModel("title3", "content3", "subreddit3", 33, "03/01/1999", "https://estereogramas.files.wordpress.com/2015/07/001.jpg");
        PostModel p4 = new PostModel("title4", "content4", "subreddit4", 0, "04/01/1999", "https://estereogramas.files.wordpress.com/2015/07/001.jpg");
        PostModel p5 = new PostModel("title5", "content5", "subreddit5", 1234, "05/01/1999", "https://estereogramas.files.wordpress.com/2015/07/001.jpg");
        mListPostModel.add(p1);
        mListPostModel.add(p2);
        mListPostModel.add(p3);
        mListPostModel.add(p4);
        mListPostModel.add(p5);
    }

    public List<PostModel> getTopPosts() {
        return mListPostModel;
    }

}
