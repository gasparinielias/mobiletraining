package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.Classes.Listing;
import ar.edu.unc.famaf.redditreader.Classes.PostModel;

/**
 * Created by mono on 22/10/16.
 */

public class Parser {
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
                try {
                    after = reader.nextString();
                } catch (IllegalStateException e) {
                    after = null;
                    reader.nextNull();
                }
            } else if (name.equals("before")) {
                try {
                    before = reader.nextString();
                } catch (IllegalStateException e) {
                    before = null;
                    reader.nextNull();
                }
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

    public PostModel readPostModel(JsonReader reader) throws IOException {
        PostModel postModel = null;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("data")) {
                postModel = readPostModelData(reader);
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return postModel;
    }

    public PostModel readPostModelData(JsonReader reader)  throws IOException {
        String post_name = "";
        String title = "";
        String subreddit = "";
        int comments = 0;
        long postDate = 0;
        String imageURL = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("name")) {
                post_name = reader.nextString();
            } else if (name.equals("subreddit")) {
                subreddit = "/r/" + reader.nextString();
            } else if (name.equals("num_comments")) {
                comments = reader.nextInt();
            } else if (name.equals("created_utc")) {
                postDate = reader.nextLong();
            } else if (name.equals("thumbnail")) {
                imageURL = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new PostModel(post_name, title, subreddit, comments, postDate, imageURL);
    }

}
