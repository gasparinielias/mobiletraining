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

    public Listing readJsonStream() throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(mIs, "UTF-8"));
        try {
            return readListing(reader);
        } finally {
            reader.close();
        }
    }

    public Listing readListing(JsonReader reader) throws IOException {
        Listing list = null;
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

    public Listing readListingData(JsonReader reader) throws IOException {
        String modhash = "";
        List<PostModel> listPostModel = null;
        String after = "";
        String before = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("modhash")) {
                modhash = reader.nextString();
            } else if (name.equals("children")) {
                listPostModel = readChildren(reader);
            } else if (name.equals("after")) {
                after = reader.nextString();
            } else if (name.equals("before")) {
                before = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();

        return new Listing(modhash, listPostModel, after, before);
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
            } else if (name.equals("created_utc")) {
                postDate = String.valueOf(reader.nextLong());
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new PostModel(title, subreddit, comments, postDate, imageURL);
    }

}
