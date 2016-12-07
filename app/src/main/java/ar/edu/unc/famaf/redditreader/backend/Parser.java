package ar.edu.unc.famaf.redditreader.backend;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unc.famaf.redditreader.classes.Listing;
import ar.edu.unc.famaf.redditreader.classes.PostModel;

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
        String after = null;
        String before = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "modhash":
                    modhash = reader.nextString();
                    break;
                case "children":
                    listPostModel = readChildren(reader);
                    break;
                case "after":
                    if (reader.peek() != JsonToken.NULL) {
                        after = reader.nextString();
                    } else {
                        reader.nextNull();
                    }
                    break;
                case "before":
                    if (reader.peek() != JsonToken.NULL) {
                        before = reader.nextString();
                    } else {
                        reader.nextNull();
                    }
                    break;
                default:
                    reader.skipValue();
                    break;
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
        String author = "";
        String subreddit = "";
        String url = "";
        int comments = 0;
        long postDate = 0;
        String thumbnailURL = "";
        String previewURL = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            switch (name) {
                case "title":
                    title = reader.nextString();
                    break;
                case "name":
                    post_name = reader.nextString();
                    break;
                case "author":
                    author = reader.nextString();
                    break;
                case "subreddit":
                    subreddit = "/r/" + reader.nextString();
                    break;
                case "url":
                    url = reader.nextString();
                    break;
                case "num_comments":
                    comments = reader.nextInt();
                    break;
                case "created_utc":
                    postDate = reader.nextLong();
                    break;
                case "thumbnail":
                    thumbnailURL = reader.nextString();
                    break;
                case "preview":
                    previewURL = readPreviewURL(reader);
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new PostModel(post_name, title, author, subreddit, url, comments, postDate, thumbnailURL, previewURL);
    }

    public String readPreviewURL(JsonReader reader) throws IOException {
        String previewURL = null;
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("images")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    reader.beginObject();
                    while (reader.hasNext()) {
                        String name = reader.nextName();
                        if (name.equals("source")) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String name2 = reader.nextName();
                                if (name2.equals("url")) {
                                    previewURL = reader.nextString();
                                } else {
                                    reader.skipValue();
                                }
                            }
                            reader.endObject();
                        } else {
                            reader.skipValue();
                        }
                    }
                    reader.endObject();
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return previewURL;
    }

}
