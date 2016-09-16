package famaf.unc.edu.ar.redditreader;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;

public class NewsActivity extends AppCompatActivity {
    static final int LOG_IN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(famaf.unc.edu.ar.redditreader.R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(famaf.unc.edu.ar.redditreader.R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(famaf.unc.edu.ar.redditreader.R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == famaf.unc.edu.ar.redditreader.R.id.action_sign_in) {
            NewsActivityFragment newsfragment = (NewsActivityFragment)
                    getSupportFragmentManager().findFragmentById(famaf.unc.edu.ar.redditreader.R.id.news_activity_fragment_id);
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
                }
                break;
        }
    }
}
