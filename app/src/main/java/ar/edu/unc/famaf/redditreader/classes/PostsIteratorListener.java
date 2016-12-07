package ar.edu.unc.famaf.redditreader.classes;

import java.util.List;

/**
 * Created by mono on 13/11/16.
 */

public interface PostsIteratorListener {
    void nextPosts(List<PostModel> posts);
}
