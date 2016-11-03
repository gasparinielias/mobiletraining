

class RedditDBHelper extends SQLiteOpenHelper {
  private static final String DATABASE_NAME = "redditPostModel";
  public static final String POST_TABLE = "redditPost";
  public static final String POST_ID = "_id";
  public static final String POST_TITLE = "title";
  public static final String POST_SUBREDDIT = "subreddit",

  public DBHelper(Context context, int version) {
    super(context, DATABASE_NAME, null, version);
  }

  @Override
  public onCreate(SQLiteDatabase db) {
    String createSentence = "create table "
            + POST_TABLE + " ("
            + POST_ID + " primary key, "
            + POST_TITLE + " text not null, "
            + POST_SUBREDDIT + "text not null"
            + ");";
    db.execSQL(createSentence);
    Log.d("DB", "Database Create");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d("DB", "Database update");
  }
  
}
