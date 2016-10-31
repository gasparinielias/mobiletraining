package famaf.unc.edu.ar.redditreader.backend;

import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import famaf.unc.edu.ar.redditreader.Classes.Listing;
import famaf.unc.edu.ar.redditreader.R;

import static java.security.AccessController.getContext;

/**
 * Created by mono on 22/10/16.
 */

public class GetTopPostsTask extends AsyncTask<URL, Integer, Listing > {

    private Context context;

    public GetTopPostsTask(Context ctx) { context = ctx; }

    @Override
    protected Listing doInBackground(URL... params) {
        Listing list = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnected()) {
            try {
                HttpURLConnection conn = (HttpURLConnection) params[0].openConnection();
                conn.setRequestMethod("GET");
                list = new Parser(conn.getInputStream()).readJsonStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(context.getResources().getString(R.string.no_internet_connection));
                    builder.show();
                }
            });
        }
        return list;
    }
}
