package ar.edu.unc.famaf.redditreader.UI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import ar.edu.unc.famaf.redditreader.Classes.BitmapByteHandler;
import ar.edu.unc.famaf.redditreader.Classes.PostModel;
import ar.edu.unc.famaf.redditreader.R;
import ar.edu.unc.famaf.redditreader.backend.RedditDB;

import static ar.edu.unc.famaf.redditreader.R.plurals.comments;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater view = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.post_row, null);
        }
        if (convertView.getTag() == null) {
            holder = new ViewHolder();
            holder.title = ((TextView) convertView.findViewById(R.id.posttitle));
            holder.subreddit = ((TextView) convertView.findViewById(R.id.postsubreddit));
            holder.comments = ((TextView) convertView.findViewById(R.id.postcomments));
            holder.postDate = ((TextView) convertView.findViewById(R.id.postdate));
            holder.imageView = ((ImageView) convertView.findViewById(R.id.postimage));
            holder.progressBar = ((ProgressBar) convertView.findViewById(R.id.progress));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.position = position;

        PostModel post = mListPostModel.get(position);

        holder.title.setText(post.getTitle());
        holder.subreddit.setText(post.getSubreddit());
        holder.comments.setText(String.format(getContext().getResources().getQuantityString(comments, post.getComments()),
                                       post.getComments()));
        holder.postDate.setText(DateUtils.getRelativeTimeSpanString(
                    post.getPostDate() * 1000,
                    System.currentTimeMillis(),
                    DateUtils.MINUTE_IN_MILLIS));

        Bitmap bitmap = new RedditDB().getPostThumbnail(getContext(), post.getName());

        if (bitmap == null) {
            URL[] urlArr = new URL[1];
            try {
                urlArr[0] = new URL(post.getThumbnailURL());

                new DownloadImageAsyncTask(position, holder, post.getName()) {
                    @Override
                    protected void onPreExecute() {
                        if (mHolder.position == mPosition) {
                            mHolder.imageView.setImageBitmap(null);
                            mHolder.progressBar.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        if (mHolder.position == mPosition) {
                            mHolder.progressBar.setVisibility(View.GONE);
                            mHolder.imageView.setImageBitmap(bitmap);
                        }

                        new RedditDB().updateBytes(
                                getContext(),
                                mPostId,
                                BitmapByteHandler.getBytes(bitmap)
                        );
                    }
                }.execute(urlArr);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.imageView.setImageBitmap(bitmap);
        }

        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return mListPostModel.isEmpty();
    }

    static class ViewHolder {
        int position;
        TextView title;
        TextView subreddit;
        TextView comments;
        TextView postDate;
        ImageView imageView;
        ProgressBar progressBar;
    }

    protected class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
        private int NO_NETWORK_SERVICE = 1;
        protected ViewHolder mHolder;
        protected int mPosition;
        protected String mPostId;

        DownloadImageAsyncTask(int position, ViewHolder holder, String postId) {
            mPosition = position;
            mHolder = holder;
            mPostId = postId;
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL url = params[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            // Check for network service
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null && ni.isConnected()) {
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    InputStream is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is, null, null);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
                return bitmap;
            } else {
                publishProgress(NO_NETWORK_SERVICE);
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            /*
            if (values[0] == NO_NETWORK_SERVICE) {
                Context context = (Context) getContext();
                Toast.makeText(context, "No network service.",
                        Toast.LENGTH_SHORT).show();
            }
            */
            super.onProgressUpdate(values);
        }
    }
}
