package famaf.unc.edu.ar.redditreader.UI;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import famaf.unc.edu.ar.redditreader.Classes.Listener;
import famaf.unc.edu.ar.redditreader.Classes.Listing;
import famaf.unc.edu.ar.redditreader.Classes.PostModel;
import famaf.unc.edu.ar.redditreader.R;
import famaf.unc.edu.ar.redditreader.backend.Backend;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment implements Listener {

    private ListView listview;
    public NewsActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(famaf.unc.edu.ar.redditreader.R.layout.fragment_news, container, false);
        listview = (ListView) rootView.findViewById(R.id.posts_list_view);
        Backend.getInstance().setListener(this);
        Backend.getInstance().getTopPosts(this.getActivity());
        return rootView;
    }


    public void nextPosts(Listing lst) {
        if (lst != null) {
            PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_row, lst.getListPostModel());
            listview.setAdapter(adapter);
        }
    }
}
