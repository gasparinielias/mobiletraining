package ar.edu.unc.famaf.redditreader.classes;

import java.util.List;

/**
 * Created by mono on 02/10/16.
 */
public class Listing {
    private String mModHash;
    private List<PostModel> mListPostModel;
    private String mAfter;
    private String mBefore;

    public Listing(String mModHash, List<PostModel> listPostModel, String after, String before) {
        this.mModHash = mModHash;
        this.mListPostModel = listPostModel;
        this.mAfter = after;
        this.mBefore = before;
    }

    public String getModHash() {
        return mModHash;
    }

    public void setModHash(String modHash) {
        this.mModHash = modHash;
    }

    public List<PostModel> getListPostModel() {
        return mListPostModel;
    }

    public void setListPostModel(List<PostModel> listPostModel) {
        mListPostModel = listPostModel;
    }

    public String getAfter() {
        return mAfter;
    }

    public void setAfter(String after) {
        this.mAfter = after;
    }

    public String getBefore() {
        return mBefore;
    }

    public void setBefore(String before) {
        this.mBefore = before;
    }
}
