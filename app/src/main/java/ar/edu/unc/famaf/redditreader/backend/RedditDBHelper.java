package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.Classes.BitmapByteHandler;
import ar.edu.unc.famaf.redditreader.Classes.PostModel;

class RedditDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "redditPostModel";

    public static final String POST_TABLE = "redditPost";
    public static final String POST_ID = "_id";
    public static final String POST_TITLE = "title";
    public static final String POST_SUBREDDIT = "subreddit";
    public static final String POST_THUMBNAIL = "thumbnail";
    public static final String THUMBNAIL_BLOB = "blob";

    public RedditDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSentence = "create table "
                + POST_TABLE + " ("
                + POST_ID + " primary key, "
                + POST_TITLE + " text not null, "
                + POST_SUBREDDIT + " text not null, "
                + POST_THUMBNAIL + " text not null, "
                + THUMBNAIL_BLOB + " BLOB"
                + ");";
        db.execSQL(createSentence);
        Log.d("DB", "Database Create");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB", "Database update");
        db.execSQL("DROP TABLE IF EXISTS " + POST_TABLE);
        this.onCreate(db);
    }

    public void insert(PostModel postModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POST_ID, postModel.getName());
        values.put(POST_TITLE, postModel.getTitle());
        values.put(POST_SUBREDDIT, postModel.getSubreddit());
        values.put(POST_THUMBNAIL, postModel.getImageURL());
        db.insert(POST_TABLE, null, values);
        db.close();
    }

    public void clean() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(POST_TABLE, null, null);
        Log.d("DB", "DELETED");
        db.close();
    }

}
