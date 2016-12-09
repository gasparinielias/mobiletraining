package ar.edu.unc.famaf.redditreader.backend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.classes.BitmapByteHandler;
import ar.edu.unc.famaf.redditreader.classes.PostModel;

public class RedditDB {
    private int RETURN_POSTS_LIMIT = 5;

    public void insert(Context context, List<PostModel> listPostModel, int subredditIndex) {
        RedditDBHelper helper = new RedditDBHelper(context);
        for (int i = 0; i < listPostModel.size(); i++) {
            helper.insert(listPostModel.get(i), subredditIndex);
        }
    }

    public void cleanDatabase(Context context) {
        RedditDBHelper helper = new RedditDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.onUpgrade(db, 0, 0);
        helper.clean();
    }

    /*
    public Listing getAllPosts(Context context) {
        RedditDBHelper helper = new RedditDBHelper(context);
        List<PostModel> list = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                helper.POST_ID,
                helper.POST_TITLE,
                helper.POST_AUTHOR,
                helper.POST_SUBREDDIT,
                helper.POST_URL,
                helper.POST_THUMBNAIL,
                helper.THUMBNAIL_BLOB,
                helper.POST_PREVIEW
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
        int authorIndex = c.getColumnIndex(helper.POST_AUTHOR);
        int subredditIndex = c.getColumnIndex(helper.POST_SUBREDDIT);
        int urlIndex = c.getColumnIndex(helper.POST_URL);
        int thumbnailIndex = c.getColumnIndex(helper.POST_THUMBNAIL);
        int blobIndex = c.getColumnIndex(helper.THUMBNAIL_BLOB);
        int previewIndex = c.getColumnIndex(helper.POST_PREVIEW);

        while (c.moveToNext()) {
            list.add(new PostModel(
                    c.getString(idIndex),
                    c.getString(titleIndex),
                    c.getString(authorIndex),
                    c.getString(subredditIndex),
                    c.getString(urlIndex),
                    0,
                    0,
                    c.getString(thumbnailIndex),
                    c.getString(previewIndex)
            ));
        }

        c.close();
        db.close();

        return new Listing("", list, "", "");
    }
    */

    public void updateBytes(Context context, String postId, byte[] bytes) {
        RedditDBHelper helper = new RedditDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

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

    public List<PostModel> getPostsAfterIndex(Context context, int postIndex, int tabIndex) {
        RedditDBHelper helper = new RedditDBHelper(context);
        List<PostModel> list = new ArrayList<>();

        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {
                helper.POST_ID,
                helper.POST_TITLE,
                helper.POST_AUTHOR,
                helper.POST_SUBREDDIT,
                helper.POST_COMMENTS,
                helper.POST_DATE,
                helper.POST_URL,
                helper.POST_THUMBNAIL,
                helper.POST_PREVIEW
        };
        String selection = helper.POST_TAB + " = ?";
        String[] selectionArgs = new String[1];
        selectionArgs[0] = String.valueOf(tabIndex);

        Cursor c = db.query(
                helper.POST_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                String.valueOf(postIndex) + ',' +
                        String.valueOf(RETURN_POSTS_LIMIT)
        );

        int idIndex = c.getColumnIndex(helper.POST_ID);
        int titleIndex = c.getColumnIndex(helper.POST_TITLE);
        int authorIndex = c.getColumnIndex(helper.POST_AUTHOR);
        int subredditIndex = c.getColumnIndex(helper.POST_SUBREDDIT);
        int commentsIndex = c.getColumnIndex(helper.POST_COMMENTS);
        int dateIndex = c.getColumnIndex(helper.POST_DATE);
        int urlIndex = c.getColumnIndex(helper.POST_URL);
        int thumbnailIndex = c.getColumnIndex(helper.POST_THUMBNAIL);
        int previewIndex = c.getColumnIndex(helper.POST_PREVIEW);

        if (c.moveToFirst()) {
            do {
                list.add(new PostModel(
                        c.getString(idIndex),
                        c.getString(titleIndex),
                        c.getString(authorIndex),
                        c.getString(subredditIndex),
                        c.getString(urlIndex),
                        c.getInt(commentsIndex),
                        c.getInt(dateIndex),
                        c.getString(thumbnailIndex),
                        c.getString(previewIndex)
                ));
            } while (c.moveToNext());
        }

        c.close();
        db.close();

        return list;
    }
}
