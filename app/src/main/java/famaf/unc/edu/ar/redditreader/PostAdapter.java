package famaf.unc.edu.ar.redditreader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.List;

import static famaf.unc.edu.ar.redditreader.R.plurals.comments;

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
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater view = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.post_row, null);

            holder = new ViewHolder();
            holder.title = ((TextView) convertView.findViewById(R.id.posttitle));
            holder.subreddit = ((TextView) convertView.findViewById(R.id.postsubreddit));
            holder.comments = ((TextView) convertView.findViewById(R.id.postcomments));
            holder.postDate = ((TextView) convertView.findViewById(R.id.postdate));
            holder.imageView = ((ImageView) convertView.findViewById(R.id.postimage));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PostModel post = mListPostModel.get(position);

        holder.title.setText(post.getTitle());
        holder.subreddit.setText(post.getSubreddit());
        holder.comments.setText(String.format(getContext().getResources().getQuantityString(comments, post.getComments()),
                                       post.getComments()));
        holder.postDate.setText(post.getPostDate());
        URL[] urlArr = new URL[1];
        try {
            urlArr[0] = new URL(post.getImageURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Falla
        //new DownloadImageAsyncTask(holder.imageView).execute(urlArr);

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mListPostModel.isEmpty();
    }

    static class ViewHolder {
        TextView title;
        TextView subreddit;
        TextView comments;
        TextView postDate;
        ImageView imageView;
    }

    protected class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
        ImageView imageViewToSet;

        public DownloadImageAsyncTask(ImageView iv) {
            super();
            imageViewToSet = iv;
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL url = params[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            // Checkear network info?
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is, null, null);
            }
            catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageViewToSet.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
