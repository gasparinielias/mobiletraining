package ar.edu.unc.famaf.redditreader.ui;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.classes.PostModel;
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
        final PostModel post = (PostModel) intent.getSerializableExtra("selectedPost");
        TextView postTitle = (TextView) rootview.findViewById(R.id.detail_posttitle);
        TextView subreddit = (TextView) rootview.findViewById(R.id.detail_subreddit);
        TextView url = (TextView) rootview.findViewById(R.id.detail_url);
        TextView author = (TextView) rootview.findViewById(R.id.detail_author);
        TextView date = (TextView) rootview.findViewById(R.id.detail_date);

        postTitle.setText(post.getTitle());
        subreddit.setText(post.getSubreddit());
        url.setText(" • " + getDomainName(post.getUrl()));
        author.setText(post.getAuthor());
        date.setText(" • " +
                DateUtils.getRelativeTimeSpanString(
                post.getPostDate() * 1000,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS));

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webViewIntent = new Intent(getActivity(), WebViewActivity.class);
                webViewIntent.putExtra("url", post.getUrl());
                startActivity(webViewIntent);
            }
        });
        return rootview;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        // Download preview image
        Intent intent = getActivity().getIntent();
        PostModel post = (PostModel) intent.getSerializableExtra("selectedPost");
        if (post.getPreviewURL() != null) {
            try {
                URL[] urlArr = new URL[1];
                urlArr[0] = new URL(post.getPreviewURL());
                new DownloadPreviewAsyncTask(getActivity()) {
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        ((ImageView) view.findViewById(R.id.detail_preview)).setImageBitmap(bitmap);
                    }
                }.execute(urlArr);
            } catch (MalformedURLException e) {
                ((ImageView) view.findViewById(R.id.detail_preview)).setVisibility(View.GONE);
            }
        }
    }

    public String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;

        } catch (URISyntaxException e) {
            e.printStackTrace();
            return url;
        }
    }

    protected class DownloadPreviewAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
        private int NO_NETWORK_SERVICE = 1;
        private Context mContext;

        public DownloadPreviewAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL url = params[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            // Check for network service
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
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
