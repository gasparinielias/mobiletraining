package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.backend.RedditDB;
import ar.edu.unc.famaf.redditreader.classes.EndlessScrollListener;
import ar.edu.unc.famaf.redditreader.classes.OnPostItemSelectedListener;
import ar.edu.unc.famaf.redditreader.classes.PostModel;
import ar.edu.unc.famaf.redditreader.classes.PostsIteratorListener;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements PostsIteratorListener {
    private ListView listview;
    private PostAdapter postAdapter;
    private List<PostModel> list = new ArrayList<>();

    public NewsActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RedditDB rdb = new RedditDB();
        rdb.cleanDatabase(getContext());

        View rootView = inflater.inflate(ar.edu.unc.famaf.redditreader.R.layout.fragment_news, container, false);
        listview = (ListView) rootView.findViewById(R.id.posts_list_view);
        listview.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Backend.getInstance().getNextPosts(NewsActivityFragment.this, getContext());
                return true;
            }
        });
        postAdapter = new PostAdapter(getContext(), R.layout.post_row, list);
        listview.setAdapter(postAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity parentActivity = getActivity();
                if (parentActivity instanceof OnPostItemSelectedListener) {
                    ((OnPostItemSelectedListener) parentActivity).onPostItemPicked(list.get(position));
                }
            }
        });

        Backend.getInstance().getNextPosts(this, this.getActivity());
        return rootView;
    }


    public void nextPosts(List<PostModel> list) {
        this.list.addAll(list);
        postAdapter.notifyDataSetChanged();
    }

    public void clearPosts() {
        list.clear();
        postAdapter.notifyDataSetChanged();
    }

    public void changeSubreddit(int subreddit_index) {
        clearPosts();
        Backend instance = Backend.getInstance();
        instance.setSubredditIndex(subreddit_index, getContext());
        instance.getNextPosts(this, this.getActivity());
    }

}
