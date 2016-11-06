package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.Classes.BitmapByteHandler;
import ar.edu.unc.famaf.redditreader.Classes.Listing;
import ar.edu.unc.famaf.redditreader.Classes.PostModel;

public class RedditDB {

    public void insert(Context context, List<PostModel> listPostModel) {
        RedditDBHelper helper = new RedditDBHelper(context);
        for (int i = 0; i < listPostModel.size(); i++) {
            helper.insert(listPostModel.get(i));
        }
    }

    public void cleanDatabase(Context context) {
        RedditDBHelper helper = new RedditDBHelper(context);
        helper.clean();
    }

    public Listing getAllPosts(Context context) {
        RedditDBHelper helper = new RedditDBHelper(context);
        List<PostModel> list = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                helper.POST_ID,
                helper.POST_TITLE,
                helper.POST_SUBREDDIT,
                helper.POST_THUMBNAIL,
                helper.THUMBNAIL_BLOB
        };

        Cursor c = db.query(
                helper.POST_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        int idIndex = c.getColumnIndex(helper.POST_ID);
        int titleIndex = c.getColumnIndex(helper.POST_TITLE);
        int subredditIndex = c.getColumnIndex(helper.POST_SUBREDDIT);
        int thumbnailIndex = c.getColumnIndex(helper.POST_THUMBNAIL);
        int blobIndex = c.getColumnIndex(helper.THUMBNAIL_BLOB);

        while (c.moveToNext()) {
            list.add(new PostModel(
                    c.getString(idIndex),
                    c.getString(titleIndex),
                    c.getString(subredditIndex),
                    0,
                    0,
                    c.getString(thumbnailIndex)
            ));
        }

        c.close();
        db.close();

        return new Listing("", list, "", "");
    }

    public void updateBytes(Context context, String postId, byte[] bytes) {
        RedditDBHelper helper = new RedditDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(helper.THUMBNAIL_BLOB, bytes);

        String selection = helper.POST_ID + " = ?";
        String[] selectionArgs = { postId };

        db.update(
                helper.POST_TABLE,
                values,
                selection,
                selectionArgs);

        db.close();
    }

    public Bitmap getPostThumbnail(Context context, String postId) {
        RedditDBHelper helper = new RedditDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Bitmap bitmap = null;

        String[] projection = {
                helper.THUMBNAIL_BLOB
        };

        String selection = helper.POST_ID + " = ?";
        String[] selectionArgs = { postId };

        Cursor c = db.query(
                helper.POST_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            int blobIndex = c.getColumnIndex(helper.THUMBNAIL_BLOB);
            bitmap = BitmapByteHandler.getImage(c.getBlob(blobIndex));
        }

        c.close();
        db.close();

        return bitmap;
    }

}
