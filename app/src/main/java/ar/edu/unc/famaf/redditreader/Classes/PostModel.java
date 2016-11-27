package ar.edu.unc.famaf.redditreader.Classes;

import java.io.Serializable;

/**
 * Created by mono on 02/10/16.
 */
public class PostModel implements Serializable {
    private String mName;
    private String mTitle;
    private String mAuthor;
    private String mSubreddit;
    private int mComments;
    private long mPostDate;
    private String mThumbnailURL;
    private String mUrl;
    private String mPreviewURL;

    public PostModel(String name, String title, String author, String subreddit, String url, int comments,
                     long postDate, String thumbnailURL, String previewURL) {
        this.mName = name;
        this.mTitle = title;
        this.mAuthor = author;
        this.mSubreddit = subreddit;
        this.mUrl = url;
        this.mComments = comments;
        this.mPostDate = postDate;
        this.mThumbnailURL = thumbnailURL;
        this.mPreviewURL = previewURL;
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

    public String getThumbnailURL() {
        return mThumbnailURL;
    }

    public void setThumbnailURL(String mImageURL) {
        this.mThumbnailURL = mImageURL;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public String getPreviewURL() {
        return mPreviewURL;
    }

    public void setPreviewURL(String mPreviewURL) {
        this.mPreviewURL = mPreviewURL;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }
}
