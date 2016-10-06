package famaf.unc.edu.ar.redditreader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mono on 02/10/16.
 */
public class PostAdapter extends ArrayAdapter<PostModel> {
    private List<PostModel> mListPostModel;

    public PostAdapter(Context context, int resource, List<PostModel> lst) {
        super(context, resource);
        mListPostModel = lst;
    }

    @Override
    public int getCount() {
        return mListPostModel.size();
    }

    @Override
    public PostModel getItem(int position) {
        return mListPostModel.get(position);
    }

    @Override
    public int getPosition(PostModel item) {
        return mListPostModel.indexOf(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater view = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.post_row, null);
        }

        PostModel post = mListPostModel.get(position);
        TextView title = ((TextView) convertView.findViewById(R.id.posttitle));
        TextView content = ((TextView) convertView.findViewById(R.id.postcontent));
        TextView subreddit = ((TextView) convertView.findViewById(R.id.postsubreddit));
        TextView comments = ((TextView) convertView.findViewById(R.id.postcomments));
        TextView postDate = ((TextView) convertView.findViewById(R.id.postdate));

        title.setText(post.getTitle());
        content.setText(post.getContent());
        subreddit .setText(post.getSubreddit());
        comments.setText(String.valueOf(post.getComments()));
        postDate.setText(post.getPostDate());

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mListPostModel.isEmpty();
    }
}
