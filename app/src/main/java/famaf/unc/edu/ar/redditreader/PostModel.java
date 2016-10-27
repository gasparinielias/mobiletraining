package famaf.unc.edu.ar.redditreader;

import java.net.URL;

/**
 * Created by mono on 02/10/16.
 */
public class PostModel {
    private String mTitle;
    private String mSubreddit;
    private int mComments;
    private String mPostDate;
    private String mImageURL;

    public PostModel(String mTitle, String mSubreddit, int mComments, String mPostDate, String mImageURL) {
        this.mTitle = mTitle;
        this.mSubreddit = mSubreddit;
        this.mComments = mComments;
        this.mPostDate = mPostDate;
        this.mImageURL = mImageURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getSubreddit() {
        return mSubreddit;
    }

    public void setSubreddit(String subreddit) {
        this.mSubreddit = subreddit;
    }

    public int getComments() {
        return mComments;
    }

    public void setComments(int comments) {
        this.mComments = comments;
    }

    public String getPostDate() {
        return mPostDate;
    }

    public void setPostDate(String postDate) {
        this.mPostDate = postDate;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }
}
