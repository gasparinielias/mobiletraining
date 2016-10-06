package famaf.unc.edu.ar.redditreader;

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
        PostModel p1 = new PostModel("title1", "content1", "subreddit1", 50, "01/01/1999");
        PostModel p2 = new PostModel("title2", "content2", "subreddit2", 40, "02/01/1999");
        PostModel p3 = new PostModel("title3", "content3", "subreddit3", 30, "03/01/1999");
        PostModel p4 = new PostModel("title4", "content4", "subreddit4", 20, "04/01/1999");
        PostModel p5 = new PostModel("title5", "content5", "subreddit5", 10, "05/01/1999");
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
