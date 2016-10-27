package famaf.unc.edu.ar.redditreader.backend;

import android.util.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import famaf.unc.edu.ar.redditreader.PostModel;

/**
 * Created by mono on 22/10/16.
 */

public class Parser {
    private List<PostModel> mListPostModels = new ArrayList<>();
    private InputStream mIs;

    public Parser(InputStream is) {
        mIs = is;
    }

    public List<PostModel> readJsonStream() throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(mIs, "UTF-8"));
        try {
            return readListing(reader);
        } finally {
            reader.close();
        }
    }

    public List<PostModel> readListing(JsonReader reader) throws IOException {
        List<PostModel> list = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                list = readListingData(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return list;
    }

    public List<PostModel> readListingData(JsonReader reader) throws IOException {
        List<PostModel> list = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("children")) {
                list = readChildren(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return list;

    }

    public List<PostModel> readChildren(JsonReader reader) throws IOException {
        List<PostModel> list = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            list.add(readPostModel(reader));
        }
        reader.endArray();
        return list;
    }

    public PostModel readPostModel(JsonReader reader)  throws IOException {
        String title = "";
        String subreddit = "";
        int comments = 0;
        String postDate = "some date";
        String imageURL = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("subreddit")) {
                subreddit = "/r/" + reader.nextString();
            } else if (name.equals("num_comments")) {
                comments = reader.nextInt();
            } else if (name.equals("")) {

            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new PostModel(title, subreddit, comments, postDate, imageURL);
    }

}
