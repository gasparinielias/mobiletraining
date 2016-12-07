package ar.edu.unc.famaf.redditreader.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ar.edu.unc.famaf.redditreader.R;

/**
 * Created by mono on 16/11/16.
 */

public class PostDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(ar.edu.unc.famaf.redditreader.R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_post_detail);
    }
}
