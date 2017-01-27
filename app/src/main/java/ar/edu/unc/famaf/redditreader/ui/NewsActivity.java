package ar.edu.unc.famaf.redditreader.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ar.edu.unc.famaf.redditreader.backend.RedditDB;
import ar.edu.unc.famaf.redditreader.classes.OnPostItemSelectedListener;
import ar.edu.unc.famaf.redditreader.classes.PostModel;
import ar.edu.unc.famaf.redditreader.R;

public class NewsActivity extends AppCompatActivity implements OnPostItemSelectedListener {
    static final int LOG_IN_REQUEST = 1;
    private String[] mSubreddits;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        RedditDB rdb = new RedditDB();
        rdb.cleanDatabase(this);
        */

        super.onCreate(savedInstanceState);
        setContentView(ar.edu.unc.famaf.redditreader.R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(ar.edu.unc.famaf.redditreader.R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(ar.edu.unc.famaf.redditreader.R.menu.menu_news, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == ar.edu.unc.famaf.redditreader.R.id.action_sign_in) {
            NewsActivityFragment newsfragment = (NewsActivityFragment)
                    getSupportFragmentManager().findFragmentById(ar.edu.unc.famaf.redditreader.R.id.news_activity_fragment_id);
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivityForResult(loginActivity, LOG_IN_REQUEST);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case (LOG_IN_REQUEST):
                if (resultCode == Activity.RESULT_OK) {
                    String useremail = data.getStringExtra("useremail");

                    /*
                    TextView textView = (TextView) findViewById(famaf.unc.edu.ar.redditreader.R.id.loginStatusTextView);

                    if (useremail != null) {
                        textView.setText("User " + useremail + " logged in");
                    }
                    */
                } else {
                    Context context = getApplicationContext();
                    Toast.makeText(context, context.getResources().getString(R.string.login_error),
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onPostItemPicked(PostModel post) {
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra("selectedPost", post);
        startActivity(intent);
    }

    public void initializeDrawer() {
        // Set the adapter for the list view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mSubreddits = getResources().getStringArray(R.array.subreddit_names);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mSubreddits);
        mDrawerList.setAdapter(adapter);
        setSelectedItem(0);

        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    protected class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            NewsActivityFragment fragment = (NewsActivityFragment) (fragmentManager.findFragmentById(R.id.fragment));
            fragment.changeSubreddit(position);

            setSelectedItem(position);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    private void setSelectedItem(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(mSubreddits[position]);
    }
}
