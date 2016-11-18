package ar.edu.unc.famaf.redditreader.Classes;

import java.io.Serializable;

/**
 * Created by mono on 02/10/16.
 */
public class PostModel implements Serializable {
    private String mName;
    private String mTitle;
    private String mSubreddit;
    private int mComments;
    private long mPostDate;
    private String mImageURL;

    public PostModel(String name, String mTitle, String mSubreddit, int mComments, long mPostDate, String mImageURL) {
        this.mName = name;
        this.mTitle = mTitle;
        this.mSubreddit = mSubreddit;
        this.mComments = mComments;
        this.mPostDate = mPostDate;
        this.mImageURL = mImageURL;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
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

    public long getPostDate() {
        return mPostDate;
    }

    public void setPostDate(long postDate) {
        this.mPostDate = postDate;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }
}
