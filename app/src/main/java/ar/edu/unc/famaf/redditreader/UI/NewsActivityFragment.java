package ar.edu.unc.famaf.redditreader.UI;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ar.edu.unc.famaf.redditreader.Classes.Listener;
import ar.edu.unc.famaf.redditreader.Classes.Listing;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.Backend;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements Listener {

    private ListView listview;
    public NewsActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(ar.edu.unc.famaf.redditreader.R.layout.fragment_news, container, false);
        listview = (ListView) rootView.findViewById(R.id.posts_list_view);
        Backend.getInstance().getTopPosts(this.getActivity(), this);
        return rootView;
    }


    public void nextPosts(Listing lst) {
        if (lst != null) {
            PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_row, lst.getListPostModel());
            listview.setAdapter(adapter);
        }
    }
}
