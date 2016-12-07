package ar.edu.unc.famaf.redditreader.backend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ar.edu.unc.famaf.redditreader.classes.BitmapByteHandler;
import ar.edu.unc.famaf.redditreader.classes.PostModel;

/**
 * Created by mono on 19/11/16.
 */

public class PostImageGetter {
    protected PostModel mPostModel;
    protected Context mContext;
    public int mPosition;

    public PostImageGetter(PostModel postModel, Context context, int position) {
        mPostModel = postModel;
        mContext = context;
        mPosition = position;
    }

    public void getThumbnail() {

        final RedditDB rdb = new RedditDB();
        Bitmap bitmap = rdb.getPostThumbnail(mContext, mPostModel.getName());
        if (bitmap == null) {
            URL[] urlArr = new URL[1];
            try {
                urlArr[0] = new URL(mPostModel.getThumbnailURL());

                new DownloadImageAsyncTask() {
                    @Override
                    protected void onPostExecute(Bitmap bitmap) {
                        rdb.updateBytes(
                                mContext,
                                mPostModel.getName(),
                                BitmapByteHandler.getBytes(bitmap)
                        );
                        PostImageGetter.this.onPostExecute(bitmap);
                    }
                }.execute(urlArr);

            } catch (MalformedURLException e) {
            }
        } else {
            onPostExecute(bitmap);
        }

    }

    public void getImagePreview() {

    }

    public void onPostExecute(Bitmap bitmap) { }

    class DownloadImageAsyncTask extends AsyncTask<URL, Integer, Bitmap> {
        private int NO_NETWORK_SERVICE = 1;

        @Override
        protected Bitmap doInBackground(URL... params) {
            URL url = params[0];
            Bitmap bitmap = null;
            HttpURLConnection connection = null;

            // Check for network service
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
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
