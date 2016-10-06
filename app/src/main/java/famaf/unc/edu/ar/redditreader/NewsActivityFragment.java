package famaf.unc.edu.ar.redditreader;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {

    public NewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(famaf.unc.edu.ar.redditreader.R.layout.fragment_news, container, false);
        ListView listview = (ListView) rootView.findViewById(R.id.posts_list_view);
        List<PostModel> lst = Backend.getInstance().getTopPosts();
        PostAdapter adapter = new PostAdapter(getContext(), R.layout.post_row, lst);
        listview.setAdapter(adapter);

        return rootView;
    }
}
