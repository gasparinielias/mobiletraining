package famaf.unc.edu.ar.redditreader.backend;

import java.util.ArrayList;
import java.util.List;

import famaf.unc.edu.ar.redditreader.PostModel;

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
        PostModel p1 = new PostModel("title1", "content1", "subreddit1", 50, "01/01/1999", "http://65.media.tumblr.com/avatar_76325297055b_128.png");
        PostModel p2 = new PostModel("title2", "content2", "subreddit2", 1, "02/01/1999", "http://nextholiday.ir/ZthemeAgency/img/android.png");
        PostModel p3 = new PostModel("title3", "content3", "subreddit3", 33, "03/01/1999", "https://s.yimg.com/wv/images/c75111a00f971abb11fd069a10c02d5e_96.jpeg");
        PostModel p4 = new PostModel("title4", "content4", "subreddit4", 0, "04/01/1999", "https://67.media.tumblr.com/avatar_1105e6452cad_128.png");
        PostModel p5 = new PostModel("title5", "content5", "subreddit5", 1234, "05/01/1999", "http://icons.iconarchive.com/icons/icons8/ios7/512/Music-Guitar-icon.png");
        mListPostModel.add(p1);
        mListPostModel.add(p2);
        mListPostModel.add(p3);
        mListPostModel.add(p4);
        mListPostModel.add(p5);
        mListPostModel.add(p1);
        mListPostModel.add(p2);
        mListPostModel.add(p3);
        mListPostModel.add(p4);
        mListPostModel.add(p5);
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
