package ar.edu.unc.famaf.redditreader.UI;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ar.edu.unc.famaf.redditreader.Classes.PostModel;
import ar.edu.unc.famaf.redditreader.R;

/**
 * Created by mono on 18/11/16.
 */

public class PostDetailActivityFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_post_detail, container, false);
        Intent intent = getActivity().getIntent();
        PostModel post = (PostModel) intent.getSerializableExtra("selectedPost");
        TextView postTitle = (TextView) rootview.findViewById(R.id.detail_posttitle);
        postTitle.setText(post.getTitle());
        return rootview;
    }
}
