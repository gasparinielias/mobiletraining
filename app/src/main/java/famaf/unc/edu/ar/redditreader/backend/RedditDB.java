
class RedditDB {
  
  void insert(Context context, List<PostModel> listPostModel) {
    RedditDBHelper helper = new RedditDBHelper(context, );
    for (int i = 0; i < lst.size(); i++) {
      helper.insert(listPostModel[i]);
    }
  }

  void clean(Context context) {
    RedditDBHelper helper = new RedditDBHelper(context, );
    // Delete everything from DB
  }

}
