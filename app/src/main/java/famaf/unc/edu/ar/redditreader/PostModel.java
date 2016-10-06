package famaf.unc.edu.ar.redditreader;

/**
 * Created by mono on 02/10/16.
 */
public class PostModel {
    private String mTitle;
    private String mContent;
    private String mSubreddit;
    private int mComments;
    private String mPostDate;

    public PostModel(String mTitle, String mContent, String mSubreddit, int mComments, String mPostDate) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mSubreddit = mSubreddit;
        this.mComments = mComments;
        this.mPostDate = mPostDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
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
}
